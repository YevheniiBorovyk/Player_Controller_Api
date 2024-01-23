package com.data;

import lombok.extern.log4j.Log4j;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;

import static com.utils.FileUtils.createDirectory;

@Log4j
public class TestData {

    private static final String USER_HOME = "user.home";

    private static final String PATH_TO_WIN_TEST_DATA_FOLDER = new File("C:/testResources/testData/").getAbsolutePath();
    private static final String PATH_TO_MAC_TEST_DATA_FOLDER =
            Paths.get(System.getProperty(USER_HOME), "Library", "testData")
                    .toString();
    private static final String PATH_TO_LINUX_TEST_DATA_FOLDER = Paths.get(System.getProperty(USER_HOME), "TestData")
            .toString();
    private static final String PATH_TO_MAC_TEST_ARTIFACTS_FOLDER =
            Paths.get(System.getProperty(USER_HOME), "Library", "testArtifacts")
                    .toString();
    private static final String PATH_TO_WIN_TEST_ARTIFACTS_FOLDER =
            new File("C:/testResources/testArtifacts/").getAbsolutePath();
    private static final String PATH_TO_LINUX_TEST_ARTIFACTS_FOLDER =
            Paths.get(System.getProperty(USER_HOME), "TestArtifacts")
                    .toString();
    public static String PATH_TO_TESTS_DATA_FOLDER;
    public static String PATH_TO_TESTS_ARTIFACTS_FOLDER;

    public static Long getTimeStamp() {
        Date currentDate = new Date();
        return currentDate.getTime() / 1000;
    }

    public static void setUpOsConfig() {
        String operatingSystem = System.getProperty("os.name");
        log.info("LocalOperatingSystem: " + operatingSystem);

        if (operatingSystem.toLowerCase()
                .contains("win")) {
            log.info("Setting WIN OS folder paths");
            PATH_TO_TESTS_DATA_FOLDER = PATH_TO_WIN_TEST_DATA_FOLDER;
            PATH_TO_TESTS_ARTIFACTS_FOLDER = PATH_TO_WIN_TEST_ARTIFACTS_FOLDER;
        } else if (operatingSystem.toLowerCase()
                .contains("linux")) {
            log.info("Setting LINUX OS folder paths");
            createDirectory(PATH_TO_LINUX_TEST_DATA_FOLDER);
            PATH_TO_TESTS_DATA_FOLDER = PATH_TO_LINUX_TEST_DATA_FOLDER;
            PATH_TO_TESTS_ARTIFACTS_FOLDER = PATH_TO_LINUX_TEST_ARTIFACTS_FOLDER;
        } else {
            log.info("Setting MAC OS folder paths");
            createDirectory(PATH_TO_MAC_TEST_DATA_FOLDER);
            PATH_TO_TESTS_DATA_FOLDER = PATH_TO_MAC_TEST_DATA_FOLDER;
            PATH_TO_TESTS_ARTIFACTS_FOLDER = PATH_TO_MAC_TEST_ARTIFACTS_FOLDER;
        }
    }
}
