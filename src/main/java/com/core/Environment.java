package com.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Environment {

    DEV(null),
    RC(null),
    PROD("https://conduit-api.bondaracademy.com/api/");

    private final String host;

    public static Environment getEnvironmentByURL(String url) {
        if (url.contains("bondaracademy.com")) {
            return PROD;
        } else if (url.contains("-rc")) {
            return RC;
        } else if (url.contains("-dev")) {
            return DEV;
        } else {
            throw new IllegalArgumentException("Cannot get environment from given url: " + url);
        }
    }
}
