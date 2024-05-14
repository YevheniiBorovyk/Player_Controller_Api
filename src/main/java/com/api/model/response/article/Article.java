package com.api.model.response.article;

import lombok.Getter;

import java.util.List;

@Getter
public class Article {

    private String slug;
    private String title;
    private String description;
    private String body;
    private boolean favorited;
    private int favoritesCount;
    private List<String> tagList;
}
