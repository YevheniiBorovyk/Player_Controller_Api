package com.interview.playercontroller;

import com.interview.api.RestClient;
import com.interview.api.apiclient.Api;
import com.interview.api.model.player.responses.GetPlayerByPlayerIdResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.IGNORE_DEFAULTS;

public class GetPlayerByPlayerIdTests {

    private Api api;

    @BeforeClass
    public void setUp() {
        RestClient restClient = new RestClient();
        api = new Api(restClient);
    }

    @Test
    public void checkGettingPlayerByPlayerId() {
        GetPlayerByPlayerIdResponse expectedPlayerIdResponse = new GetPlayerByPlayerIdResponse
                .Builder(1, "supervisor","testSupervisor", "testSupervisor",
                "male",28,"supervisor")
                .build();
        GetPlayerByPlayerIdResponse actualPlayerIdResponse = api.playerControllerApi.getPlayerByPlayerId(1);
        assertReflectionEquals(expectedPlayerIdResponse, actualPlayerIdResponse, IGNORE_DEFAULTS);
    }
}
