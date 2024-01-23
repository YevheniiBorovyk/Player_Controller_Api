package com.api.model.player.responses;

public class GetPlayerByPlayerIdResponse {

    public Integer age;
    public String gender;
    public Integer id;
    public String login;
    public String password;
    public String role;
    public String screenName;

    public static class Builder {

        private final GetPlayerByPlayerIdResponse getPlayerByPlayerIdResponse;

        public Builder(int id, String login, String password, String screenName, String gender, int age, String role) {
            this.getPlayerByPlayerIdResponse = new GetPlayerByPlayerIdResponse();
            this.getPlayerByPlayerIdResponse.id = id;
            this.getPlayerByPlayerIdResponse.login = login;
            this.getPlayerByPlayerIdResponse.password = password;
            this.getPlayerByPlayerIdResponse.screenName = screenName;
            this.getPlayerByPlayerIdResponse.gender = gender;
            this.getPlayerByPlayerIdResponse.age = age;
            this.getPlayerByPlayerIdResponse.role = role;
        }

        public GetPlayerByPlayerIdResponse build() {
            return getPlayerByPlayerIdResponse;
        }
    }
}
