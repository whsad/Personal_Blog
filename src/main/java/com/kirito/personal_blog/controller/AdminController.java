package com.kirito.personal_blog.controller;

import com.kirito.personal_blog.common.pojos.Article;
import com.kirito.personal_blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ArticleService articleService;

    // 管理页面
    @GetMapping("/home")
    public String adminPage(Model model) {
        List<Article> list = articleService.list();
        model.addAttribute("list", list);
        return "admin";
    }

    // 新增页面
    @GetMapping("/new")
    public String newPage(Model model) {
        model.addAttribute("type", "new");
        return "articleManager";
    }

    // 新增文章
    @PostMapping("/new")
    public String createArticle(@ModelAttribute Article content){
        articleService.addArticle(content);
        return "redirect:/admin/home";
    }

    // 修改文章
    @PostMapping("/edit")
    public String updateArticle(@ModelAttribute Article article){
        articleService.updateArticle(article);
        return "redirect:/admin/home";
    }

    // 修改页面回显数据
    @GetMapping("/edit/{Id}")
    public String editPage(@PathVariable String Id, Model model) {
        model.addAttribute("type", "edit");
        Article article = articleService.findArticleById(Id);
        article.setContent(article.getContent().replace("<br>", "\n"));
        model.addAttribute("article", article);
        return "articleManager";
    }

    // 删除文章
    @PostMapping("/delete")
    public String deleteArticle(@RequestParam("Id") String Id){
        //System.out.println(Id);
        articleService.deleteArticle(Id);
        return "redirect:/admin/home";
    }

}
