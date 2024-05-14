package com.api.apiclient;

import com.api.RestClient;
import com.api.model.request.oauth.LoginRequest;
import com.api.model.request.oauth.LoginUser;
import com.api.model.request.user.User;
import com.api.model.request.user.UserRequest;
import com.api.model.response.user.UserResponse;
import com.utils.ApiHelper;
import io.qameta.allure.Step;

public class UserApi {

    private final RestClient restClient;

    public UserApi(RestClient restClient) {
        this.restClient = restClient;
    }

    @Step
    public UserResponse login(String email, String password) {
        LoginRequest loginRequest = LoginRequest.builder()
                .user(new LoginUser(email, password))
                .build();
        return ApiHelper.executeAndGetResponseBody(restClient.userService.login(loginRequest));
    }

    @Step
    public String getToken(String email, String password) {
        return login(email, password).getUser()
                .getToken();
    }

    @Step
    public UserResponse changeUserBio(User user) {
        UserRequest userRequest = UserRequest.builder()
                .user(user)
                .build();
        return ApiHelper.executeAndGetResponseBody(restClient.userService.changeUserData(userRequest));
    }
}
