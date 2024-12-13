package com.kirito.personal_blog.common.pojos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Data
public class Article {

    private String id;

    private String title;

    private LocalDate publishTime;

    private LocalDate updateTime;

    private String content;

    private String showPub;


    public Article() {
    }

    public Article(Article article){
        this.id = UUID.randomUUID().toString();
        this.title = article.title;
        LocalDate localDate = LocalDate.parse(article.getShowPub());
        this.updateTime = localDate;
        this.publishTime = localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        this.showPub = localDate.format(formatter);
        this.content = article.content;
    }

    public Article(String id, String title, String publishTime, String updateTime, String content, String showPub){
        this.id = id;
        this.title = title;
        this.publishTime = LocalDate.parse(publishTime);
        this.updateTime = LocalDate.parse(updateTime);
        this.content = content;
        this.showPub = showPub;
    }

    public String convertArticleToJson(){
        return "{"
                + "\"id\":\"" + id + "\","
                + "\"title\":\"" + title + "\","
                + "\"publishTime\":\"" + publishTime + "\","
                + "\"updateTime\":\"" + updateTime + "\","
                + "\"content\":\"" + content + "\","
                + "\"showPub\":\"" + showPub + "\""
                + "}";
    }

    public static Article parseArticleFromJson(String json){
        String[] jsonArr = json.substring(1, json.length() - 1)
                .replace("\"", "")
                .split(",");
        String id = jsonArr[0].split(":")[1];
        String title = jsonArr[1].split(":")[1];
        String publishTime = jsonArr[2].split(":")[1];
        String updateTime = jsonArr[3].split(":")[1];
        String content = jsonArr[4].split(":")[1];
        String showPub = jsonArr[5].split(":")[1];

        return new Article(
                id,
                title,
                publishTime,
                updateTime,
                content,
                showPub
        );
    }
}
