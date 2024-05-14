package com.api;

import com.api.apiclient.Api;
import com.api.service.ArticleService;
import com.api.service.UserService;
import com.core.Environment;
import com.interceptors.AssertResponseInterceptor;
import com.interceptors.HTTPLoggerInterceptor;
import com.interceptors.UserTokenInterceptor;
import com.utils.ObjectMapperUtil;
import lombok.extern.log4j.Log4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

import static com.core.Environment.getEnvironmentByURL;
import static com.data.TestData.ENVIRONMENT;

@Log4j
public class RestClient {

    private static final int DEFAULT_CONNECTION_TIMEOUT = 90;
    private static Environment testEnvironment;
    private final Retrofit retrofit;
    private final Builder builder;

    // Interceptors
    private final Interceptors interceptors = new Interceptors();
    // List of services
    public UserService userService;
    public ArticleService articleService;

    public RestClient() {
        this(new Builder(ENVIRONMENT.getHost()));
    }

    public RestClient(Builder builder) {
        this.builder = builder;
        OkHttpClient client = builder.okhttp.addInterceptor(interceptors.userToken)
                .addInterceptor(interceptors.assertResponse)
                .addInterceptor(interceptors.httpLogger)
                .build();

        this.retrofit =
                builder.retrofit.addConverterFactory(JacksonConverterFactory.create(ObjectMapperUtil.getObjectMapper()))
                        .client(client)
                        .build();

        userService = retrofit.create(UserService.class);
        articleService = retrofit.create(ArticleService.class);
    }

    public Interceptors getInterceptors() {
        return interceptors;
    }

    public Builder getBuilder() {
        return this.builder;
    }

    public static class Builder {

        private final RestClient restClient;
        private final Api api;
        private final OkHttpClient.Builder okhttp;
        private final Retrofit.Builder retrofit = new Retrofit.Builder();
        private String userEmail;
        private String userToken;

        public Builder() {
            this(ENVIRONMENT.getHost());
        }

        public Builder(String apiHostUrl) {
            this(apiHostUrl, DEFAULT_CONNECTION_TIMEOUT);
        }

        public Builder(int timeoutInSeconds) {
            this(ENVIRONMENT.getHost(), timeoutInSeconds);
        }

        public Builder(String apiHostUrl, int timeoutInSeconds) {
            retrofit.baseUrl(apiHostUrl);
            okhttp = new OkHttpClient.Builder().connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                    .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                    .readTimeout(timeoutInSeconds, TimeUnit.SECONDS);
            testEnvironment = getEnvironmentByURL(apiHostUrl);
            restClient = new RestClient(this);
            api = new Api(restClient);
        }

        public String getUserEmail() {
            return this.userEmail;
        }

        public String getUserToken() {
            return this.userToken;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            okhttp.addInterceptor(interceptor);
            return this;
        }

        public RestClient build() {
            return build(this.userEmail);
        }

        public RestClient build(String email) {
            return build(email, "qwerty");
        }

        public RestClient build(String email, String password) {
            if (null != email && null != password) {
                this.userToken = api.userApi.getToken(email, password);
                this.userEmail = email;
            }
            return restClient;
        }
    }

    public class Interceptors {

        public AssertResponseInterceptor assertResponse = new AssertResponseInterceptor(90);
        public HTTPLoggerInterceptor httpLogger = new HTTPLoggerInterceptor();
        public UserTokenInterceptor userToken = new UserTokenInterceptor(); // Создаем экземпляр UserTokenInterceptor
    }
}
