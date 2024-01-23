package com.playercontroller;

import com.api.RestClient;
import com.api.apiclient.Api;
import com.api.model.player.responses.UpdatePlayerResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import retrofit2.Response;

public class UpdatePlayerNegativeTests {

    private Api api;

    @BeforeClass
    public void setUp() {
        RestClient restClient = new RestClient();
        restClient.getInterceptors().assertResponse.isEnabled(false);
        api = new Api(restClient);
    }

    @Test
    public void checkUpdatePlayerWithNonExistentId() {
        Response<UpdatePlayerResponse> updatePlayerResponse =
                api.playerControllerApi.updatePlayerAndGetErrorBody("admin", 23);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updatePlayerResponse.code(), 403);
        softAssert.assertEquals(updatePlayerResponse.body(), null);
        softAssert.assertAll();
    }
}
