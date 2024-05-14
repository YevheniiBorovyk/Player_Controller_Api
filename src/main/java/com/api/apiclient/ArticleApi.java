package com.api.apiclient;

import com.api.RestClient;
import com.api.model.request.article.Article;
import com.api.model.request.article.ArticlesRequest;
import com.api.model.response.article.ArticleResponse;
import com.api.model.response.article.ArticlesResponse;
import com.utils.ApiHelper;
import io.qameta.allure.Step;
import okhttp3.ResponseBody;

public class ArticleApi {

    private final RestClient restClient;

    public ArticleApi(RestClient restClient) {
        this.restClient = restClient;
    }

    @Step
    public ArticleResponse createArticle(String title, String description, String body) {
        ArticlesRequest createArticlesRequest = ArticlesRequest.builder()
                .article(Article.builder()
                        .title(title)
                        .description(description)
                        .body(body)
                        .build())
                .build();
        return ApiHelper.executeAndGetResponseBody(restClient.articleService.createArticles(createArticlesRequest));
    }

    @Step
    public ArticlesResponse getArticles() {
        return ApiHelper.executeAndGetResponseBody(restClient.articleService.getArticles());
    }

    @Step
    public ArticleResponse getArticle(String articleSlug) {
        return ApiHelper.executeAndGetResponseBody(restClient.articleService.getArticle(articleSlug));
    }

    @Step
    public void deleteAllOwnArticles(String authorName) {
        getArticles().getArticles()
                .stream()
                .filter(article -> authorName.equals(article.getAuthor()
                        .getUsername()))
                .map(article -> restClient.articleService.deleteArticle(article.getSlug()))
                .forEach(ApiHelper::execute);
    }

    @Step
    public ResponseBody selectArticleAsFavorite(String articleSlug) {
        return ApiHelper.executeAndGetResponseBody(restClient.articleService.selectArticleAsFavorite(articleSlug));
    }

    @Step
    public ResponseBody deleteArticleAsFavorite(String articleSlug) {
        return ApiHelper.executeAndGetResponseBody(restClient.articleService.deleteArticleAsFavorite(articleSlug));
    }
}
