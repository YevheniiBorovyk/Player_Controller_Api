package com.api.model.response.user;

import lombok.Getter;

@Getter
public class User {

    private Integer id;
    private String email;
    private String userName;
    private String bio;
    private String image;
    private String token;
}
