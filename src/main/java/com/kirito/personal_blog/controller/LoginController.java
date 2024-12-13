package com.kirito.personal_blog.controller;

import com.kirito.personal_blog.common.dtos.LoginDto;
import com.kirito.personal_blog.config.PersonalBlogConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private PersonalBlogConfig personalBlogConfig;

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto dto, Model model){
        boolean flag = personalBlogConfig.getAccount()
                .stream()
                .anyMatch(account -> account
                        .getUsername()
                        .equals(dto.getUsername()) && account
                        .getPassword()
                        .equals(dto.getPassword()));
        if (!flag){
            model.addAttribute("result", true);
            return "login";
        }
        if (dto.getUsername().equals("admin")){
            return "redirect:/admin";
        }
        return "redirect:/home";
    }
}
