package com.kirito.personal_blog.controller;

import com.kirito.personal_blog.common.pojos.Article;
import com.kirito.personal_blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/new")
    public String createArticle(@ModelAttribute Article content){
        articleService.addArticle(content);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String updateArticle(@ModelAttribute Article article){
        articleService.updateArticle(article);
        return "redirect:/admin";
    }
}
