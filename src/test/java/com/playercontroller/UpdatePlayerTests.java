package com.playercontroller;

import com.api.RestClient;
import com.api.apiclient.Api;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.utils.StringMan.getRandomString;

public class UpdatePlayerTests {

    private Api api;

    @BeforeClass
    public void setUp() {
        RestClient restClient = new RestClient();
        api = new Api(restClient);
    }

    @Test
    public void checkUpdatingPlayer() {
        String newScreenName = getRandomString(4);
        api.playerControllerApi.updatePlayer("supervisor", 1003435220, newScreenName, "male", 17);
        Assert.assertEquals(api.playerControllerApi.getPlayerByPlayerId(1003435220).screenName, newScreenName,
                "Screen name assertion failed");
    }
}
