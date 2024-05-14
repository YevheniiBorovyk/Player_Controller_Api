package com.api.model.request.user;

import lombok.Builder;

@Builder
public class User {

    private String email;
    private String userName;
    private String bio;
    private String image;
    private String token;
}
