package com.utils;

import com.moczul.ok2curl.CurlBuilder;
import com.moczul.ok2curl.Options;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Objects;

public class ApiHelper {

    private static final String ERROR_OCCURRED_WHILE_EXECUTING_REQUEST =
            "An error occurred while executing the request.";

    public static <T> Response<T> execute(Call<T> call) {
        try {
            return call.execute();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    ERROR_OCCURRED_WHILE_EXECUTING_REQUEST + System.lineSeparator() + generateCurl(call.request()), e);
        }
    }

    public static <T> T executeAndGetResponseBody(Call<T> call) {
        try {
            return Objects.requireNonNull(call.execute()
                    .body(), "Expected response body is 'null'. Call execution failed." + System.lineSeparator() +
                    generateCurl(call.request()));
        } catch (IOException e) {
            throw new UncheckedIOException(
                    ERROR_OCCURRED_WHILE_EXECUTING_REQUEST + System.lineSeparator() + generateCurl(call.request()), e);
        }
    }

    public static <T> String executeAndGetErrorBody(Call<T> call) {
        try {
            return Objects.requireNonNull(call.execute()
                                    .errorBody(),
                            "Expected error body is 'null'. Call execution failed." + System.lineSeparator() +
                                    generateCurl(call.request()))
                    .string();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    ERROR_OCCURRED_WHILE_EXECUTING_REQUEST + System.lineSeparator() + generateCurl(call.request()), e);
        }
    }

    public static String generateCurl(Request request) {
        return new CurlBuilder(request.newBuilder()
                .build(), 1024L * 1024L, new ArrayList<>(), Options.EMPTY).build();
    }
}
