package com.kirito.personal_blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class LoginController {

    // 登录页面
    @GetMapping({"/", "/login"})
    public String loginPage() {
        return "login";
    }

}
