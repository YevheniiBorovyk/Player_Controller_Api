package com.interceptors;

import com.moczul.ok2curl.CurlBuilder;
import com.moczul.ok2curl.Options;
import com.utils.DateTimeUtils;
import lombok.extern.log4j.Log4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.utils.AllureAttachment.txtAttachment;
import static com.utils.DateTimeUtils.getCurrentDateTime;

@Log4j
public class HTTPLoggerInterceptor implements Interceptor {

    private boolean isEnabled = true;
    private boolean disableOnce = false;

    public void isEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void disableOnce() {
        disableOnce = true;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isEnabled) {
            return chain.proceed(chain.request());
        }
        if (disableOnce) {
            log.info("HTTP LOGGER IS DISABLED FOR NEXT REQUEST");
            try {
                return chain.proceed(chain.request());
            } finally {
                disableOnce = false;
            }
        }

        String threadName = Thread.currentThread()
                .getName();
        StringBuffer allLogs = new StringBuffer();
        String attachmentName = "REQUEST: ";

        Request request = chain.request();
        String curl = generateCurl(request);

        Buffer curlBufferedSource = new Buffer();
        curlBufferedSource.write(curl.getBytes());
        if (isPlainText(curlBufferedSource)) {
            allLogs.append("cURL: \n" + curl);
        }
        allLogs.append("\n-------REQUEST LOG-------");
        allLogs.append("\n[" + threadName + "]---> " + request.method() + " " + request.url());
        attachmentName += request.method() + " " + request.url()
                .encodedPath();

        long startNs = System.nanoTime();
        String requestStartTime = DateTimeUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");

        Response response = proceedRequest(chain, threadName, allLogs, attachmentName, request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        String requestFinishTime = DateTimeUtils.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");

        String requestAllure = getAllureViewRequest(request, requestStartTime);
        String responseAllure = getAllureViewResponse(threadName, allLogs, response, requestFinishTime, tookMs);

        writeLog(allLogs, curl, requestAllure, responseAllure);
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample of code points to
     * detect unicode control characters commonly used in binary file signatures.
     */
    private boolean isPlainText(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            buffer.copyTo(prefix, 0, buffer.size() / 2);
            for (int i = 0; i < prefix.size(); i++) {
                if (prefix.exhausted()) {
                    break;
                }
                if (Character.isISOControl(prefix.readUtf8CodePoint())) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }

    private String getAllureViewRequest(Request request, String requestStartTime) {
        return "Time: " + requestStartTime + "\n" + "Url: " + request.url() + "\n\n";
    }

    private String getAllureViewResponse(String threadName, StringBuffer allLogs, Response response,
            String requestFinishTime, long tookMs) throws IOException {
        String responseBodyJson = getAndAppendResponseBody(threadName, allLogs, response, tookMs);
        String responseUrl = getResponseTimeLog(response, tookMs, requestFinishTime) + "\n\n";
        String responseBodyAllure = "Response:\n" + responseBodyJson + "\n\n";
        return responseUrl + responseBodyAllure;
    }

    private String generateCurl(Request request) {
        return new CurlBuilder(request.newBuilder()
                .build(), 1024L * 1024L, new ArrayList<>(), Options.EMPTY).build();
    }

    private String getAndAppendResponseBody(String threadName, StringBuffer logs, Response response, long tookMs)
            throws IOException {
        ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        logs.append("\n[" + threadName + "]<--- " + response.code() + ' ' + response.message() + ' ' +
                response.request()
                        .url() + " (" + tookMs + "ms" + (", " + bodySize + " body") + ')');
        String strResponse = responseBody.string();
        Buffer responseBufferedSource = new Buffer();
        responseBufferedSource.write(strResponse.getBytes());
        if (isPlainText(responseBufferedSource) && !strResponse.isEmpty()) {
            logs.append("\n[" + threadName + "]<--- RESPONSE BODY:\n" + strResponse);
        }
        logs.append("\n[" + threadName + "]<--- END HTTP\n");
        return strResponse;
    }

    private String getResponseTimeLog(Response response, long tookMs, String requestFinishTime) throws IOException {
        int responseCode = response.code();
        ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        return "Time: " + requestFinishTime + "\n" + "Code: " + responseCode + "\n" + "Url: " + response.request()
                .url() + " (" + tookMs + "ms" + (", " + bodySize + " body") + ')';
    }

    private Response proceedRequest(Chain chain, String threadName, StringBuffer sb, String attachmentName,
            Request request) throws IOException {
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            sb.append("\n[" + threadName + "]<--- HTTP FAILED: " + e);
            log.error(e.getMessage());
            writeErrorLog(sb, attachmentName);
            throw e;
        }
        return response;
    }

    private void writeLog(StringBuffer allLogs, String curl, String requestViewAllure, String responseViewAllure) {
        log.info(allLogs.toString());
        txtAttachment("Curl", curl);
        txtAttachment("Request", requestViewAllure);
        txtAttachment("Response", responseViewAllure);
    }

    private void writeErrorLog(StringBuffer allLogs, String attachmentName) {
        log.error(allLogs.toString());
        txtAttachment(attachmentName, allLogs.toString());
    }
}
