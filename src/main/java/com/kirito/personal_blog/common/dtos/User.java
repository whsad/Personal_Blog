package com.kirito.personal_blog.common.dtos;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String username;
    private String password;
    private List<String> roles;
}
