package com.interview.api.apiclient;

import com.interview.api.RestClient;

public class Api {

    private final RestClient restClient;
    public PlayerControllerApi playerControllerApi;

    public Api(RestClient restClient) {
        this.restClient = restClient;
        init();
    }

    public void init() {
        playerControllerApi = new PlayerControllerApi(restClient);;
    }

    public RestClient getRestClient() {
        return this.restClient;
    }
}
