package com.utils;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;

import java.io.File;

@Log4j
public final class FileUtils {

    private FileUtils() {
    }

    @Step
    public static boolean createDirectory(String path) {
        File folder = new File(path);
        if (!(folder.exists() && folder.isDirectory())) {
            log.info("Create directory: " + path);
            folder.setReadable(true, false);
            folder.setExecutable(true, false);
            folder.setWritable(true, false);
            return folder.mkdirs();
        }
        return true;
    }
}
