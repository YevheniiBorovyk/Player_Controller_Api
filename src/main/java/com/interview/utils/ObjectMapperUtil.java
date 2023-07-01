package com.interview.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {

    private static final ObjectMapper objectMapper =
            new ObjectMapper().setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private ObjectMapperUtil() {
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
