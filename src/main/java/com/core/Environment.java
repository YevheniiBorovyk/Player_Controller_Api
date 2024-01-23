package com.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Environment {

    LOCAL("http://3.68.165.45"),
    DEV(null),
    RC(null),
    PROD(null);

    private final String host;
}
