package com.api.apiclient;

import com.api.RestClient;
import com.api.model.player.requests.DeletePlayer;
import com.api.model.player.requests.GetPlayerByPlayerId;
import com.api.model.player.requests.UpdatePlayer;
import com.api.model.player.responses.CreatePlayerResponse;
import com.api.model.player.responses.DeletePlayerResponse;
import com.api.model.player.responses.ErrorResponse;
import com.api.model.player.responses.GetAllPlayersResponse;
import com.api.model.player.responses.GetPlayerByPlayerIdResponse;
import com.api.model.player.responses.UpdatePlayerResponse;
import com.utils.ApiHelper;
import com.utils.ObjectMapperUtil;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class PlayerControllerApi {

    private final RestClient restClient;

    public PlayerControllerApi(RestClient restClient) {
        this.restClient = restClient;
    }

    @Step
    public CreatePlayerResponse createPlayer() {
        return createPlayer("admin", "21", "male", "loginName",
                "password", "role", "screeName");
    }

    @Step
    public CreatePlayerResponse createPlayer(String editor, String age, String gender, String login, String password,
                                             String role, String screenName) {
        return ApiHelper.executeAndGetResponseBody(restClient.playerController.createPlayer(editor, age, gender, login, password, role, screenName));
    }

    @Step
    @SneakyThrows
    public ErrorResponse createPlayerAndGetErrorBody(String editor, String age, String gender, String login,
                                                     String password, String role, String screenName) {
        String errorResponse = ApiHelper.executeAndGetErrorBody(restClient.playerController
                .createPlayer(editor, age, gender, login, password, role, screenName));
        return ObjectMapperUtil.getObjectMapper().readValue(errorResponse, ErrorResponse.class);
    }

    @Step
    public DeletePlayerResponse deletePlayer(String editor, Integer playerId) {
        DeletePlayer deletePlayer = new DeletePlayer(playerId);
        return ApiHelper.executeAndGetResponseBody(restClient.playerController.deletePlayer(editor, deletePlayer));
    }

    @Step
    public Response<DeletePlayerResponse> deletePlayerAndGetErrorBody(String editor, Integer playerId) {
        DeletePlayer deletePlayer = new DeletePlayer(playerId);
       return ApiHelper.execute(restClient.playerController.deletePlayer(editor, deletePlayer));
    }

    @Step
    public GetPlayerByPlayerIdResponse getPlayerByPlayerId(Integer playerId) {
        GetPlayerByPlayerId getPlayerByPlayerId = new GetPlayerByPlayerId(playerId);
        return ApiHelper.executeAndGetResponseBody(restClient.playerController.getPlayerByPlayerId(getPlayerByPlayerId));
    }

    @Step
    public Response<ResponseBody> getPlayerByPlayerIdAndGetErrorBody(Integer playerId) {
        GetPlayerByPlayerId getPlayerByPlayerId = new GetPlayerByPlayerId(playerId);
        return ApiHelper.execute(restClient.playerController.getResponseBodyPlayerByPlayerId(getPlayerByPlayerId));
    }

    @Step
    public GetAllPlayersResponse getAllPlayers() {
        return ApiHelper.executeAndGetResponseBody(restClient.playerController.getAllPlayers());
    }

    @Step
    public UpdatePlayerResponse updatePlayer(String editor, int id, String screenName, String gender, int age) {
        UpdatePlayer updatePlayer = UpdatePlayer.builder()
                .age(age)
                .gender(gender)
                .screenName(screenName)
                .build();
        return ApiHelper.executeAndGetResponseBody(restClient.playerController.updatePlayer(editor, id, updatePlayer));
    }

    @Step
    public Response<UpdatePlayerResponse> updatePlayerAndGetErrorBody(String editor, int id) {
        UpdatePlayer updatePlayer = UpdatePlayer.builder()
                .build();
        return ApiHelper.execute(restClient.playerController.updatePlayer(editor, id, updatePlayer));
    }
}
