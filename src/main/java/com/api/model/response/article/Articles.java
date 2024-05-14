package com.api.model.response.article;

import lombok.Getter;

import java.util.List;

@Getter
public class Articles {

    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private Integer favoritesCount;
    private Author author;
}
