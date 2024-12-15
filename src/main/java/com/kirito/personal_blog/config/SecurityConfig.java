package com.kirito.personal_blog.config;

import com.kirito.personal_blog.handler.CustomAuthenticationFailureHandler;
import com.kirito.personal_blog.handler.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/font/**", "/css/**").permitAll() // 静态资源允许匿名访问
                .antMatchers("/admin/**").hasAuthority("ADMIN") // 需要 ADMIN 权限
                .antMatchers("/base/**").hasAnyAuthority("USER", "ADMIN") // 需要 USER 或 ADMIN 权限
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // 自定义登录页面
                .loginProcessingUrl("/Login")
                .successHandler(new CustomAuthenticationSuccessHandler()) // 设置自定义成功处理器
                .failureHandler(new CustomAuthenticationFailureHandler()) // 设置自定义失败处理器
                .permitAll();

        // Spring Security 默认启用了 CSRF 保护，未携带正确的 CSRF Token 时，POST 请求会被拦截并返回 403 Forbidden。
        // todo 昨天一通操作没什么问题，403forbidden的原因是没有开启 csrf.disable()
        // 目前测试没什么问题，明天再看看
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
