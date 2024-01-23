package com.api.model.player.responses;

public class Player {

    public Integer id;
    public String screenName;
    public String gender;
    public Integer age;

    public static class Builder {

        private final Player player;

        public Builder(int id, String screenName, String gender, int age) {
            this.player = new Player();
            this.player.id = id;
            this.player.screenName = screenName;
            this.player.gender = gender;
            this.player.age = age;
        }

        public Player build() {
            return player;
        }
    }
}
