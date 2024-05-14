package com.api.model.request.article;

import lombok.Builder;

import java.util.List;

@Builder
public class Article {

    private String title;
    private String description;
    private String body;
    private List<String> tagList;
}
