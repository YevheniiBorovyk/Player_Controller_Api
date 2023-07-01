package com.interview.playercontroller;

import com.interview.api.RestClient;
import com.interview.api.apiclient.Api;
import com.interview.api.model.player.responses.ErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreatePlayerNegativeTests {

    private Api api;

    @BeforeClass
    public void setUp() {
        RestClient restClient = new RestClient();
        restClient.getInterceptors().assertResponse.isEnabled(false);
        api = new Api(restClient);
    }

    @Test
    public void checkCreatingPlayerWithoutRequiredParameters() {
        ErrorResponse errorResponse = api.playerControllerApi.createPlayerAndGetErrorBody("admin",
                "21", null, null, null, null, null);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(errorResponse.status, "400");
        softAssert.assertEquals(errorResponse.error, "Bad Request");
        softAssert.assertEquals(errorResponse.message, StringUtils.EMPTY);
        softAssert.assertEquals(errorResponse.path, "/player/create/admin");
        softAssert.assertAll();
    }
}
