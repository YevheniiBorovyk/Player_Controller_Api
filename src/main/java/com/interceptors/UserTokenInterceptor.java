package com.interceptors;

import com.api.model.response.user.UserResponse;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

import static com.utils.ObjectMapperUtil.getObjectMapper;

public class UserTokenInterceptor implements Interceptor {

    private String token;
    private boolean isEnabled = true;

    public void isEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (!isEnabled) {
            return chain.proceed(chain.request());
        }
        if (originalRequest.method()
                .equals("POST") && originalRequest.url()
                .toString()
                .contains("users/login")) {
            Response response = chain.proceed(originalRequest);
            if (response.isSuccessful()) {
                ResponseBody body = response.peekBody(Long.MAX_VALUE);
                token = getObjectMapper().readValue(body.string(), UserResponse.class)
                        .getUser()
                        .getToken();
                System.out.println("Token retrieved: " + token); // Логирование получения токена
                return response;
            }
        }
        if (originalRequest.headers()
                .get("Authorization") != null && originalRequest.headers()
                .get("Authorization")
                .contains("Basic")) {
            return chain.proceed(chain.request());
        } else {
            return chain.proceed(chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build());
        }
    }
}
