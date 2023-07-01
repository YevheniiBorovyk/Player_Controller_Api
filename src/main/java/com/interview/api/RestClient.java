package com.interview.api;

import com.interview.api.service.PlayerController;
import com.interview.core.Environment;
import com.interview.interceptors.AssertResponseInterceptor;
import com.interview.interceptors.HTTPLoggerInterceptor;
import com.interview.utils.ObjectMapperUtil;
import lombok.extern.log4j.Log4j;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

import static com.interview.utils.MavenProperties.getProfileId;

@Log4j
public class RestClient {

    private static final int DEFAULT_CONNECTION_TIMEOUT = 90;

    // Interceptors
    private final Interceptors interceptors = new Interceptors();
    // List of services
    public PlayerController playerController;

    public RestClient() {
        this(Environment.valueOf(getProfileId().toUpperCase()));
    }

    public RestClient(Environment environment) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptors.assertResponse)
                .addInterceptor(interceptors.httpLogger)
                .build();

       Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(environment.getHost())
                .addConverterFactory(JacksonConverterFactory.create(ObjectMapperUtil.getObjectMapper()))
                .build();

        playerController = retrofit.create(PlayerController.class);
    }

    public Interceptors getInterceptors() {
        return interceptors;
    }

    public class Interceptors {

        public AssertResponseInterceptor assertResponse = new AssertResponseInterceptor(90);
        public HTTPLoggerInterceptor httpLogger = new HTTPLoggerInterceptor();
    }
}
