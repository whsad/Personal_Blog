package com.kirito.personal_blog.controller;

import com.kirito.personal_blog.common.pojos.Article;
import com.kirito.personal_blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/base")
public class BaseController {

    @Autowired
    private ArticleService articleService;

    // 首页
    @GetMapping("/home")
    public String list(Model model) {
        List<Article> list = articleService.list();
        model.addAttribute("list", list);
        return "index";
    }

    // 查看文章
    @GetMapping("/article/{Id}")
    public String findArticleById(@PathVariable String Id, Model model) {
        articleService.findArticleById(Id, model);
        return "article";
    }
}
