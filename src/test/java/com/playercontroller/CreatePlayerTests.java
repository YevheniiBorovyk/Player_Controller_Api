package com.playercontroller;

import com.api.RestClient;
import com.api.apiclient.Api;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreatePlayerTests {

    private Api api;

    @BeforeClass
    public void setUp() {
        RestClient restClient = new RestClient();
        api = new Api(restClient);
    }

    @Test
    public void checkCreatingPlayer() {
        api.playerControllerApi.createPlayer();
    }
}
