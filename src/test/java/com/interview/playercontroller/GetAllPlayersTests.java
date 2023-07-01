package com.interview.playercontroller;

import com.interview.api.RestClient;
import com.interview.api.apiclient.Api;
import com.interview.api.model.player.responses.GetAllPlayersResponse;
import com.interview.api.model.player.responses.Player;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.IGNORE_DEFAULTS;

public class GetAllPlayersTests {

    private Api api;

    @BeforeClass
    public void setUp() {
        RestClient restClient = new RestClient();
        api = new Api(restClient);
    }

    @Test
    public void checkGettingAllPlayers() {
        Player expectedPlayer = new Player.Builder(1003435220, "g2SCT", "male", 17).build();
        GetAllPlayersResponse getAllPlayersResponse = api.playerControllerApi.getAllPlayers();
        Assert.assertEquals(getAllPlayersResponse.players.size(), 2, "Wrong players size");
        assertReflectionEquals(getAllPlayersResponse.players.get(1), expectedPlayer, IGNORE_DEFAULTS);
    }
}
