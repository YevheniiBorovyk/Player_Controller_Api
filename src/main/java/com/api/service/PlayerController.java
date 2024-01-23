package com.api.service;

import com.api.model.player.requests.DeletePlayer;
import com.api.model.player.requests.GetPlayerByPlayerId;
import com.api.model.player.requests.UpdatePlayer;
import com.api.model.player.responses.CreatePlayerResponse;
import com.api.model.player.responses.DeletePlayerResponse;
import com.api.model.player.responses.GetAllPlayersResponse;
import com.api.model.player.responses.GetPlayerByPlayerIdResponse;
import com.api.model.player.responses.UpdatePlayerResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlayerController {

    @GET("player/create/{editor}")
    Call<CreatePlayerResponse> createPlayer(@Path("editor") String editor,
                                            @Query("age") String age,
                                            @Query("gender") String gender,
                                            @Query("login") String login,
                                            @Query("password") String password,
                                            @Query("role") String role,
                                            @Query("screenName") String screenName);

    @HTTP(method = "DELETE", path = "player/delete/{editor}", hasBody = true)
    Call<DeletePlayerResponse> deletePlayer(@Path("editor") String editor,
                                            @Body DeletePlayer deletePlayer);

    @POST("player/get")
    Call<ResponseBody> getResponseBodyPlayerByPlayerId(@Body GetPlayerByPlayerId getPlayerByPlayerId);

    @POST("player/get")
    Call<GetPlayerByPlayerIdResponse> getPlayerByPlayerId(@Body GetPlayerByPlayerId getPlayerByPlayerId);

    @GET("player/get/all")
    Call<GetAllPlayersResponse> getAllPlayers();

    @PATCH("player/update/{editor}/{id}")
    Call<UpdatePlayerResponse> updatePlayer(@Path("editor") String editor,
                                            @Path("id") Integer id,
                                            @Body UpdatePlayer updatePlayer);
}
