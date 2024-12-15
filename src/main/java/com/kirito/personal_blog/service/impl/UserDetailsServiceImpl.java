package com.kirito.personal_blog.service.impl;

import com.kirito.personal_blog.common.dtos.LoginUser;
import com.kirito.personal_blog.common.dtos.User;
import com.kirito.personal_blog.config.PersonalBlogConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PersonalBlogConfig personalBlogConfig;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = personalBlogConfig.getAccount()
                .stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (user == null){
            throw new RuntimeException("Invalid username or password.");
        }

        return new LoginUser(user);
    }
}
