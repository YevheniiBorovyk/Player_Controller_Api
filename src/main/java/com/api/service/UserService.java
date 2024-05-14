package com.api.service;

import com.api.model.request.oauth.LoginRequest;
import com.api.model.request.user.UserRequest;
import com.api.model.response.user.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {

    @POST("users/login")
    Call<UserResponse> login(@Body LoginRequest loginUserRequest);

    @PUT("user")
    Call<UserResponse> changeUserData(@Body UserRequest userRequest);
}
