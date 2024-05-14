package com.api.apiclient;

import com.api.RestClient;

public class Api {

    private final RestClient restClient;
    public UserApi userApi;
    public ArticleApi articleApi;

    public Api() {
        this(new RestClient.Builder().build());
    }

    public Api(String registeredUserEmail) {
        this(registeredUserEmail, "qwerty");
    }

    public Api(String registeredUserEmail, String password) {
        this(new RestClient.Builder().build(registeredUserEmail, password));
    }

    public Api(RestClient restClient) {
        this.restClient = restClient;
        init();
    }

    public void init() {
        userApi = new UserApi(restClient);
        articleApi = new ArticleApi(restClient);
    }

    public RestClient getRestClient() {
        return this.restClient;
    }
}
