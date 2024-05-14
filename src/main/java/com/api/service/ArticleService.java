package com.api.service;

import com.api.model.request.article.ArticlesRequest;
import com.api.model.response.article.ArticleResponse;
import com.api.model.response.article.ArticlesResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ArticleService {

    @POST("articles")
    Call<ArticleResponse> createArticles(@Body ArticlesRequest CreateArticlesRequest);

    @GET("articles")
    Call<ArticlesResponse> getArticles();

    @GET("articles/{articleSlug}")
    Call<ArticleResponse> getArticle(@Path("articleSlug") String articleSlug);

    @DELETE("articles/{articleSlug}")
    Call<Void> deleteArticle(@Path("articleSlug") String articleSlug);

    @POST("articles/{articleSlug}/favorite")
    Call<ResponseBody> selectArticleAsFavorite(@Path("articleSlug") String articleSlug);

    @DELETE("articles/{articleSlug}/favorite")
    Call<ResponseBody> deleteArticleAsFavorite(@Path("articleSlug") String articleSlug);
}
