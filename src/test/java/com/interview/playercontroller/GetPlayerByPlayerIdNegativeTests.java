package com.interview.playercontroller;

import com.interview.api.RestClient;
import com.interview.api.apiclient.Api;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import retrofit2.Response;

public class GetPlayerByPlayerIdNegativeTests {

    private Api api;

    @BeforeClass
    public void setUp() {
        RestClient restClient = new RestClient();
        api = new Api(restClient);
    }

    @Test
    public void checkGettingPlayerByPlayerIdWithNonExistentId() {
       Response<ResponseBody> responseBody = api.playerControllerApi
                .getPlayerByPlayerIdAndGetErrorBody(2065855471);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseBody.message(), StringUtils.EMPTY);
        softAssert.assertEquals(responseBody.code(), 200);
        softAssert.assertAll();
    }
}
