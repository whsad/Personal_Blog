package com.kirito.personal_blog.controller;

import com.kirito.personal_blog.common.pojos.Article;
import com.kirito.personal_blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class PageController {

    @Autowired
    private ArticleService articleService;

    // 登录页面
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    // 首页
    @GetMapping("/home")
    public String list(Model model){
        List<Article> list = articleService.list();
        model.addAttribute("list", list);
        return "index";
    }

    // 管理页面
    @GetMapping("/admin")
    public String adminPage(Model model){
        List<Article> list = articleService.list();
        model.addAttribute("list", list);
        return "admin";
    }

    // 新增页面
    @GetMapping("/new")
    public String newPage(Model model){
        model.addAttribute("type", "new");
        return "articleManager";
    }

    // 修改页面
    @GetMapping("/edit/{Id}")
    public String editPage(@PathVariable String Id, Model model){
        model.addAttribute("type", "edit");
        articleService.findArticleById(Id, model);
        return "articleManager";
    }

    // 查看文章页面
    @GetMapping("/article/{Id}")
    public String findArticleById(@PathVariable String Id, Model model){
        articleService.findArticleById(Id, model);
        return "article";
    }

    private static List<Article> testData() {
        List<Article> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Article article = new Article();
            article.setId(i+"");
            article.setTitle("新闻" + i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH); // 指定为英文
            String formattedDate = LocalDate.now().format(formatter);
            article.setShowPub(formattedDate);
            list.add(article);
        }
        return list;
    }
}
