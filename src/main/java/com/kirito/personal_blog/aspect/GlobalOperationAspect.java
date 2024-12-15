package com.kirito.personal_blog.aspect;

import com.kirito.personal_blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "GlobalOperationAspect")
public class GlobalOperationAspect {

    @Autowired
    private ArticleService articleService;

    @After("within(com.kirito.personal_blog.controller.AdminController))")
    public void afterRequest(){
        log.info("Request has finished, saving data");
        articleService.saveArticles();
    }
}
