package com.data;

import com.core.Environment;
import lombok.extern.log4j.Log4j;

import static com.utils.PropertiesUtils.getEnv;

@Log4j
public class TestData {

    public static final Environment ENVIRONMENT = getEnvironment();

    private static Environment getEnvironment() {
        String envParameter = getEnv();
        try {
            return Environment.valueOf(envParameter.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ExceptionInInitializerError("Not supported environment format: [" + envParameter + "].\n" +
                    "Please use one of these: prod / rc / dev");
        }
    }
}
