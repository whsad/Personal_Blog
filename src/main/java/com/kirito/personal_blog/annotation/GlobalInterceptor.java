package com.kirito.personal_blog.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalInterceptor {

    /**
     * 校验登录
     */
    boolean checkLogin() default true;

    /**
     * 校验管理员
     */
    boolean checkAdmin() default false;
}
