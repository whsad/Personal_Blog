package com.kirito.personal_blog.aspect;

import com.kirito.personal_blog.annotation.GlobalInterceptor;
import com.kirito.personal_blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Slf4j(topic = "GlobalOperationAspect")
public class GlobalOperationAspect {

    @Autowired
    private ArticleService articleService;

    @Pointcut("@annotation(com.kirito.personal_blog.annotation.GlobalInterceptor)")
    private void requestInterceptor(){
    }

    @After("within(com.kirito.personal_blog.controller.ArticleController))")
    public void afterRequest(){
        log.info("Request has finished, saving data");
        articleService.saveArticles();
    }

    @Before("requestInterceptor()")
    public void interceptor(JoinPoint point){
        try {
            Object target = point.getTarget();
            String methodName = point.getSignature().getName();
            Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);

            if (interceptor == null) return;

            if (interceptor.checkLogin() || interceptor.checkAdmin())
                checkLogin(interceptor.checkAdmin());
        }catch (Exception e){
            log.error("Global interceptor exception", e);
        }
    }

    private void checkLogin(boolean checkAdmin) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (Objects.isNull(user)) {
            log.info("User not logged in, redirecting to login page.");
            response.sendRedirect("/login"); // 未登录重定向到登录页面
            return;
        }

        if (checkAdmin && !user.equals("admin")){
            log.info("User is not admin, access denied.");
            response.sendRedirect("/403"); // 无管理员权限，重定向到403页面
        }

    }
}
