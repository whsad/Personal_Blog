package com.kirito.personal_blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "personalblog")
public class PersonalBlogConfig {
    private List<Account> account;

    @Data
    public static class Account{
        private String username;
        private String password;
    }
}
