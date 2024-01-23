package com.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AppTokenInterceptor implements Interceptor {

    private static final String APP_TOKEN_PROD = "=";
    private static final String APP_TOKEN_RC = "=";
    private static final String APP_TOKEN_DEV = "=";

    private boolean isEnabled = true;
    private String basicToken;

    public void isEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setBasicToken(String basicToken) {
        this.basicToken = basicToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = chain.request()
                .url()
                .toString();
        Request originalRequest = chain.request();
        if (!isEnabled) {
            return chain.proceed(originalRequest);
        }
        if (originalRequest.headers()
                .get("Authorization") != null) {
            if (originalRequest.headers()
                    .get("Authorization")
                    .contains("Basic")) {
                Request requestWithAppToken = originalRequest.newBuilder()
                        .header("Authorization", "Basic " + getAppToken(url))
                        .build();
                return chain.proceed(requestWithAppToken);
            }
        }
        return chain.proceed(originalRequest);
    }

    public String getAppToken(String url) {
        if (basicToken == null) {
            if (url.contains("-prod")) {
                return APP_TOKEN_PROD;
            } else if (url.contains("-rc")) {
                return APP_TOKEN_RC;
            } else if (url.contains("-dev")) {
                return APP_TOKEN_DEV;
            } else {
                throw new IllegalArgumentException("Environment " + url + " is not supported");
            }
        } else {
            return basicToken;
        }
    }
}
