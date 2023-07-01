package com.interview.utils;

import io.qameta.allure.Attachment;

public final class AllureAttachment {

    @Attachment(value = "{0}", type = "text/plain")
    public static String txtAttachment(String name, String text) {
        return text;
    }
}
