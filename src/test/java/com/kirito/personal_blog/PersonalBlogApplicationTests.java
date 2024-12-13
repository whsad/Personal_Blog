package com.kirito.personal_blog;

import com.kirito.personal_blog.common.pojos.Article;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@SpringBootTest
class PersonalBlogApplicationTests {

    @Test
    void contextLoads() {
        Article article = new Article();
        article.setId("1");
        article.setTitle("test");
        article.setPublishTime(LocalDate.now());
        article.setUpdateTime(LocalDate.now());
        article.setContent("text");
        article.setShowPub("2024-12-12");
        String json = article.convertArticleToJson();
        System.out.println(json);
        Article article1 = Article.parseArticleFromJson(json);
        System.out.println(article1.getId());
        System.out.println(article1.getTitle());
        System.out.println(article1.getContent());
        System.out.println(article1.getPublishTime());
        System.out.println(article1.getUpdateTime());
        System.out.println(article1.getShowPub());
    }

    @Test
    void test1() throws IOException {
        File file = new ClassPathResource("file").getFile();
        System.out.println(file);
    }

    @Test
    void test2 () {
        System.out.println(System.getProperty("user.dir"));

    }

}
