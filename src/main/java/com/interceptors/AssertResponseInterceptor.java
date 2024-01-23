package com.interceptors;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.moczul.ok2curl.CurlBuilder;
import com.moczul.ok2curl.Options;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


public class AssertResponseInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssertResponseInterceptor.class);

    private boolean isEnabled = true;
    private boolean disableOnce = false;
    private final int connectionTimeoutInSeconds;

    public AssertResponseInterceptor(int connectionTimeoutInSeconds) {
        this.connectionTimeoutInSeconds = connectionTimeoutInSeconds;
    }

    public void isEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void disableOnce() {
        disableOnce = true;
    }

    @Override
    public Response intercept(Chain chain) {
        if (!isEnabled) {
            return proceedRequest(chain);
        }
        if (disableOnce) {
            LOGGER.info("ASSERT DISABLED FOR NEXT REQUEST");
            try {
                return proceedRequest(chain);
            } finally {
                disableOnce = false;
            }
        }
        Request request = chain.request();
        Response response = proceedRequest(chain);
        String method = request.method();
        try {
            Assert.assertTrue(response.isSuccessful(),
                    method + " " + request.url() + " RESPONSE CODE: " + response.code() + " MESSAGE: " +
                            response.peekBody(Long.MAX_VALUE)
                                    .string() + "\n\n" + generateCurl(request));
        } catch (IOException e) {
            throw new UncheckedIOException("An error occurred while peek response body." + System.lineSeparator() +
                    generateCurl(chain.request()), e);
        }
        return response;
    }

    private Response proceedRequest(Chain chain) {
        try {
            return chain.proceed(chain.request());
        } catch (MismatchedInputException e) {
            throw new UncheckedIOException(
                    "Cannot deserialize value." + System.lineSeparator() + generateCurl(chain.request()), e);
        } catch (SocketTimeoutException e) {
            throw new UncheckedIOException(
                    "Cannot get response from server after " + connectionTimeoutInSeconds + " seconds timeout." +
                            System.lineSeparator() + generateCurl(chain.request()), e);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "An error occurred while proceed executing the request." + System.lineSeparator() +
                            generateCurl(chain.request()), e);
        }
    }

    private String generateCurl(Request request) {
        return new CurlBuilder(request.newBuilder()
                .build(), 1024L * 1024L, new ArrayList<>(), Options.EMPTY).build();
    }
}
