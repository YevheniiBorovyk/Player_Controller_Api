package com.interview.utils;

import com.interview.core.Environment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MavenProperties {

    private static final String PARAM_FILE = "test_parameters.properties";

    private static String getParameter(String propName) {
        String system = System.getProperty(propName);
        if (system == null || system.isEmpty()) {
            system = getProperties().getProperty(propName);
            log.info("System Property " + propName + ": " + system);
            try {
                System.setProperty(propName, system);
            } catch (NullPointerException e) {

            }
        }
        return system;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        String file;
        try {
            file = URLDecoder.decode(MavenProperties.class.getClassLoader()
                    .getResource(PARAM_FILE)
                    .getFile(), StandardCharsets.UTF_8);
            properties.load(new FileInputStream(file));
        } catch (Exception e) {
            throw new RuntimeException("Cannot find properties file: " + PARAM_FILE, e);
        }
        return properties;
    }

    public static String getProfileId() {
        return getParameter("profileId");
    }
}
