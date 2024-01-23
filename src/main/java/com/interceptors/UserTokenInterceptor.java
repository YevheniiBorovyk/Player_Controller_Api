package com.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class UserTokenInterceptor implements Interceptor {

    private String accessToken;
    private boolean isEnabled = true;

    public void isEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getToken() {
        return accessToken;
    }

    public void setToken(String token) {
        accessToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (!isEnabled) {
            return chain.proceed(chain.request());
        }
/*        if (originalRequest.method()
                .equals("POST") && originalRequest.url()
                .toString()
                .contains()) {//"route which give you token"
            Response response = chain.proceed(originalRequest);
            if (response.isSuccessful()) {
                ResponseBody body = response.peekBody(Long.MAX_VALUE);
                accessToken = getObjectMapper().readValue(body.string(), Token.class).accessToken;
                return response;
            }
        }*/
        if (originalRequest.headers()
                .get("Authorization") != null && originalRequest.headers()
                .get("Authorization")
                .contains("Basic")) {
            return chain.proceed(chain.request());
        } else {
            return chain.proceed(chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build());
        }
    }
}
