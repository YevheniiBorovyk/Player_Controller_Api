package com.interview.utils;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomStringUtils;

@Log4j
public class StringMan {

    @Step
    public static String getRandomString(int length) {
        String generatedRandomText = RandomStringUtils.random(length, true, true);
        log.info("Generated random string: [" + generatedRandomText + "]");
        return generatedRandomText;
    }
}
