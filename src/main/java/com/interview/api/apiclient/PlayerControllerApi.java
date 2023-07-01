package com.interview.api.apiclient;

import com.interview.api.RestClient;
import com.interview.api.model.player.requests.DeletePlayer;
import com.interview.api.model.player.requests.GetPlayerByPlayerId;
import com.interview.api.model.player.requests.UpdatePlayer;
import com.interview.api.model.player.responses.CreatePlayerResponse;
import com.interview.api.model.player.responses.DeletePlayerResponse;
import com.interview.api.model.player.responses.ErrorResponse;
import com.interview.api.model.player.responses.GetAllPlayersResponse;
import com.interview.api.model.player.responses.GetPlayerByPlayerIdResponse;
import com.interview.api.model.player.responses.UpdatePlayerResponse;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.interview.utils.ApiHelper.execute;
import static com.interview.utils.ApiHelper.executeAndGetErrorBody;
import static com.interview.utils.ApiHelper.executeAndGetResponseBody;
import static com.interview.utils.ObjectMapperUtil.getObjectMapper;

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
        return executeAndGetResponseBody(restClient.playerController.createPlayer(editor, age, gender, login, password, role, screenName));
    }

    @Step
    @SneakyThrows
    public ErrorResponse createPlayerAndGetErrorBody(String editor, String age, String gender, String login,
                                                     String password, String role, String screenName) {
        String errorResponse = executeAndGetErrorBody(restClient.playerController
                .createPlayer(editor, age, gender, login, password, role, screenName));
        return getObjectMapper().readValue(errorResponse, ErrorResponse.class);
    }

    @Step
    public DeletePlayerResponse deletePlayer(String editor, Integer playerId) {
        DeletePlayer deletePlayer = new DeletePlayer(playerId);
        return executeAndGetResponseBody(restClient.playerController.deletePlayer(editor, deletePlayer));
    }

    @Step
    public Response<DeletePlayerResponse> deletePlayerAndGetErrorBody(String editor, Integer playerId) {
        DeletePlayer deletePlayer = new DeletePlayer(playerId);
       return execute(restClient.playerController.deletePlayer(editor, deletePlayer));
    }

    @Step
    public GetPlayerByPlayerIdResponse getPlayerByPlayerId(Integer playerId) {
        GetPlayerByPlayerId getPlayerByPlayerId = new GetPlayerByPlayerId(playerId);
        return executeAndGetResponseBody(restClient.playerController.getPlayerByPlayerId(getPlayerByPlayerId));
    }

    @Step
    public Response<ResponseBody> getPlayerByPlayerIdAndGetErrorBody(Integer playerId) {
        GetPlayerByPlayerId getPlayerByPlayerId = new GetPlayerByPlayerId(playerId);
        return execute(restClient.playerController.getResponseBodyPlayerByPlayerId(getPlayerByPlayerId));
    }

    @Step
    public GetAllPlayersResponse getAllPlayers() {
        return executeAndGetResponseBody(restClient.playerController.getAllPlayers());
    }

    @Step
    public UpdatePlayerResponse updatePlayer(String editor, int id, String screenName, String gender, int age) {
        UpdatePlayer updatePlayer = UpdatePlayer.builder()
                .age(age)
                .gender(gender)
                .screenName(screenName)
                .build();
        return executeAndGetResponseBody(restClient.playerController.updatePlayer(editor, id, updatePlayer));
    }

    @Step
    public Response<UpdatePlayerResponse> updatePlayerAndGetErrorBody(String editor, int id) {
        UpdatePlayer updatePlayer = UpdatePlayer.builder()
                .build();
        return execute(restClient.playerController.updatePlayer(editor, id, updatePlayer));
    }
}
