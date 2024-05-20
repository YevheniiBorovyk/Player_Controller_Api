package com.project;

import com.api.apiclient.Api;
import com.api.model.response.article.Article;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SelectAndDeleteArticleAsFavoriteTest {

    @Test
    public void checkCreatingArticle() {
        Api api = new Api("jon01@gmail.com");
        api.articleApi.deleteAllOwnArticles("Euge");
        String articleSlug = api.articleApi.createArticle("title", "description", "body")
                .getArticle()
                .getSlug();
        api.articleApi.selectArticleAsFavorite(articleSlug);
        Article expectedArticleAfterSelectingAsFavorite = api.articleApi.getArticle(articleSlug)
                .getArticle();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(expectedArticleAfterSelectingAsFavorite.isFavorited(), "Favorited is not 'true'");
        softAssert.assertEquals(expectedArticleAfterSelectingAsFavorite.getFavoritesCount(), 1,
                "Favorite count assertion failed");

        api.articleApi.deleteArticleAsFavorite(articleSlug);
        Article expectedArticleDeletingAsFavorite = api.articleApi.getArticle(articleSlug)
                .getArticle();
        softAssert.assertFalse(expectedArticleDeletingAsFavorite.isFavorited(), "Favorited is not 'false'");
        softAssert.assertEquals(expectedArticleDeletingAsFavorite.getFavoritesCount(), 0,
                "Favorite count assertion failed after delete article as favorite");
        softAssert.assertAll();
    }
}
