package com.kirito.personal_blog.config;

import com.kirito.personal_blog.common.dtos.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "personalblog")
public class PersonalBlogConfig {
    private List<User> account;
}
