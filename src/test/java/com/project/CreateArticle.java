package com.project;

import com.api.apiclient.Api;
import com.api.model.response.article.Article;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateArticle {

    private static final String ARTICLE_TITLE = "articleTitle";
    private static final String ARTICLE_DESCRIPTION = "articleDescription";
    private static final String ARTICLE_BODY = "articleBody";

    @Test
    public void checkCreatingArticle() {
        Api api = new Api("jon01@gmail.com");
        api.articleApi.deleteAllOwnArticles("Euge");
        String articleSlug = api.articleApi.createArticle(ARTICLE_TITLE, ARTICLE_DESCRIPTION, ARTICLE_BODY)
                .getArticle()
                .getSlug();

        SoftAssert softAssert = new SoftAssert();
        Article article = api.articleApi.getArticle(articleSlug)
                .getArticle();
        softAssert.assertEquals(article.getTitle(), ARTICLE_TITLE, "Article title assertion failed");
        softAssert.assertEquals(article.getDescription(), ARTICLE_DESCRIPTION, "Article description assertion failed");
        softAssert.assertEquals(article.getBody(), ARTICLE_BODY, "Article body assertion failed");
        softAssert.assertAll();
    }
}
