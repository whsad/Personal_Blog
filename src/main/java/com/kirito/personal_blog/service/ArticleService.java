package com.kirito.personal_blog.service;

import com.kirito.personal_blog.common.pojos.Article;
import org.springframework.ui.Model;

import java.security.PrivateKey;
import java.util.List;

public interface ArticleService {

    void addArticle(Article article);

    void saveArticles();

    List<Article> list();

    Article findArticleById(String id);

    void findArticleById(String id, Model model);

    boolean updateArticle(Article article);

    void deleteArticle(String id);
}
