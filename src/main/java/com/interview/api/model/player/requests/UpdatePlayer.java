package com.interview.api.model.player.requests;

import lombok.Builder;

@Builder
public class UpdatePlayer {

    public Integer age;
    public String gender;
    public String login;
    public String password;
    public String role;
    public String screenName;
}
