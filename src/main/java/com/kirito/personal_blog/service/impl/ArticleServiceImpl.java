package com.kirito.personal_blog.service.impl;

import com.kirito.personal_blog.common.pojos.Article;
import com.kirito.personal_blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    private LinkedList<Article> articles;
    private final static String ARTICLE_PATH =
            System.getProperty("user.dir")
            + File.separator
            + "\\src\\main\\resources\\static\\file\\Article.json";

    // 初始化文章列表
    @PostConstruct
    public void initArticle(){
        articles = loadArticles();
    }

    // 新增文章
    @Override
    public void addArticle(Article content) {
        content.setContent(content.getContent()
                .replace("\n", "<br>")
                .replace("\r", ""));
        Article article = new Article(content);
        articles.addFirst(article);
    }

    // 获取所有文章
    @Override
    public List<Article> list() {
        return articles;
    }

    @Override
    public Article findArticleById(String id){
        return articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Article not found with ID: " + id));
    }

    // 根据Id查找文章
    @Override
    public void findArticleById(String id, Model model) {
        articles.stream()
                .filter(article -> article.getId().equals(id)) // 筛选除匹配的文章
                .findFirst() // 获取第一个匹配的文章
                .ifPresent(article -> model.addAttribute("article", article));
    }

    // 修改文章
    @Override
    public boolean updateArticle(Article article) {
        String id = article.getId();
        for (int i = 0; i < articles.size(); i++) {
            Article target = articles.get(i);
            if (target.getId().equals(id)){
                article.setContent(article.getContent().replace("\n", "<br>"));
                // 更新时间
                LocalDate publishTime = LocalDate.parse(article.getShowPub());
                article.setPublishTime(publishTime);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
                article.setShowPub(publishTime.format(formatter));
                articles.set(i, article);
                return true;
            }
        }
        throw new IllegalArgumentException("Article not found with ID: " + id);
    }

    // 删除文章
    @Override
    public void deleteArticle(String id) {
        Article article = findArticleById(id);
        articles.remove(article);
    }

    // 保存文章到Json文件
    @Override
    public void saveArticles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARTICLE_PATH))) {
            writer.write("[\n");
            for (int i = 0; i < articles.size(); i++) {
                // 获取文章的 JSON 格式，并将换行符替换为 "<br>"
                String articleJson = articles.get(i).convertArticleToJson()
                        .replace("\n", "<br>")
                        .replace("\r", "<br>");
                writer.write(articleJson);
                if (i < articles.size() - 1){
                    writer.write(",\n");// 添加逗号隔离
                }
            }
            writer.write("\n]"); // Json 数组结束
            writer.flush();
        } catch (IOException e) {
            log.error("Error saving articles: {}", e.getMessage());
        }
    }

    // 从 JSON 文件加载任务
    private LinkedList<Article> loadArticles() {
        File file = new File(ARTICLE_PATH);
        if (!file.exists()){
            return new LinkedList<>();
        }

        LinkedList<Article> stored_article = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            if (sb.length() == 0){
                return stored_article;
            }
            parseArticlesFromJson(sb.toString(), stored_article);
        } catch (IOException e) {
            log.error("Error loading articles: {}", e.getMessage());
        }
        return stored_article;
    }

    private void parseArticlesFromJson(String json, List<Article> storedArticle) {
        json = json.trim();
        if (json.equals("[]")) return;
        // 去除头尾[]
        json = json.substring(1, json.length() - 1);
        // 获取每一篇文章
        String[] articles = json.split("],");
        for (String articleJson : articles) {
            if (!articleJson.endsWith("}")){
                articleJson = articleJson + "}";
            }
            storedArticle.add(Article.parseArticleFromJson(articleJson));
        }
    }
}
