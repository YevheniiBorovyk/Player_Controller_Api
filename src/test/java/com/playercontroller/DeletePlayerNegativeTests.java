package com.playercontroller;

import com.api.RestClient;
import com.api.apiclient.Api;
import com.api.model.player.responses.DeletePlayerResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import retrofit2.Response;

public class DeletePlayerNegativeTests {

    private Api api;

    @BeforeClass
    public void setUp() {
        RestClient restClient = new RestClient();
        restClient.getInterceptors().assertResponse.isEnabled(false);
        api = new Api(restClient);
    }

    @Test
    public void checkDeletingPlayerWithNonExistentId() {
        Response<DeletePlayerResponse> deletePlayerResponse =
                api.playerControllerApi.deletePlayerAndGetErrorBody("admin", 2065855471);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deletePlayerResponse.code(), 403);
        softAssert.assertEquals(deletePlayerResponse.body(), null);
        softAssert.assertAll();
    }
}
