package com.api.model.request.oauth;

import lombok.Builder;

@Builder
public class LoginRequest {

    private LoginUser user;
}
