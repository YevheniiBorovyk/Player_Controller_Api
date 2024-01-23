package com.utils;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.awaitility.core.ConditionTimeoutException;
import org.awaitility.core.ThrowingRunnable;
import org.hamcrest.Matcher;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@Log4j
public class WaitUtils {

    private static final long DEFAULT_TIMEOUT = 10L;
    private static final long DEFAULT_POLL_INTERVAL = 250L;

    private WaitUtils() {
        throw new IllegalStateException("Utility class");
    }

    @Step
    public static <T> T until(Callable<T> supplier, Matcher<? super T> matcher) {
        return until(supplier, matcher, null);
    }

    @Step
    public static <T> T until(Callable<T> supplier, Matcher<? super T> matcher, String errorMessage) {
        return until(supplier, matcher, DEFAULT_TIMEOUT, DEFAULT_POLL_INTERVAL, errorMessage);
    }

    @Step
    public static <T> T until(Callable<T> supplier, Matcher<? super T> matcher, long timeoutInSeconds) {
        return until(supplier, matcher, timeoutInSeconds, DEFAULT_POLL_INTERVAL);
    }

    @Step
    public static <T> T until(Callable<T> supplier, Matcher<? super T> matcher, long timeoutInSeconds,
            String errorMessage) {
        return until(supplier, matcher, timeoutInSeconds, DEFAULT_POLL_INTERVAL, errorMessage);
    }

    @Step
    public static <T> T until(Callable<T> supplier, Matcher<? super T> matcher, long timeoutInSeconds,
            long pollingInMillis) {
        return until(supplier, matcher, timeoutInSeconds, pollingInMillis, null);
    }

    @Step
    public static <T> T until(Callable<T> supplier, Matcher<? super T> matcher, long timeoutInSeconds,
            long pollingInMillis, String errorMessage) {
        return await(errorMessage).atMost(timeoutInSeconds, SECONDS)
                .pollInSameThread()
                .pollInterval(pollingInMillis, MILLISECONDS)
                .until(supplier, matcher);
    }

    @Step
    public static void until(Callable<Boolean> condition) {
        until(condition, DEFAULT_TIMEOUT, DEFAULT_POLL_INTERVAL, null);
    }

    @Step
    public static void until(Callable<Boolean> condition, long timeoutInSeconds) {
        until(condition, timeoutInSeconds, DEFAULT_POLL_INTERVAL, null);
    }

    @Step
    public static void until(Callable<Boolean> condition, long timeoutInSeconds, long pollingInMillis) {
        until(condition, timeoutInSeconds, pollingInMillis, null);
    }

    @Step
    public static void until(Callable<Boolean> condition, String errorMessage) {
        until(condition, DEFAULT_TIMEOUT, DEFAULT_POLL_INTERVAL, errorMessage);
    }

    @Step
    public static void until(Callable<Boolean> condition, long timeoutInSeconds, String errorMessage) {
        until(condition, timeoutInSeconds, DEFAULT_POLL_INTERVAL, errorMessage);
    }

    @Step
    public static void until(Callable<Boolean> condition, long timeoutInSeconds, long pollingInMillis,
            String errorMessage) {
        await(errorMessage).atMost(timeoutInSeconds, SECONDS)
                .pollInSameThread()
                .pollInterval(pollingInMillis, MILLISECONDS)
                .until(condition);
    }

    @Step
    public static <T> T waitAndCallUntil(Callable<T> supplier, Matcher<? super T> matcher) {
        return waitAndCallUntil(supplier, matcher, null);
    }

    @Step
    public static <T> T waitAndCallUntil(Callable<T> supplier, Matcher<? super T> matcher, String errorMessage) {
        return waitAndCallUntil(supplier, matcher, DEFAULT_TIMEOUT, 1, errorMessage);
    }

    @Step
    public static <T> T waitAndCallUntil(Callable<T> supplier, Matcher<? super T> matcher, long timeoutInSeconds,
            long pollDelayInSeconds) {
        return waitAndCallUntil(supplier, matcher, timeoutInSeconds, pollDelayInSeconds, null);
    }

    @Step
    public static <T> T waitAndCallUntil(Callable<T> supplier, Matcher<? super T> matcher, long timeoutInSeconds,
            long pollDelayInSeconds, String errorMessage) {
        return await(errorMessage).atMost(timeoutInSeconds, SECONDS)
                .pollDelay(pollDelayInSeconds, SECONDS)
                .pollInSameThread()
                .pollInterval(DEFAULT_POLL_INTERVAL, MILLISECONDS)
                .until(supplier, matcher);
    }

    /**
     * @param assertion – the supplier that is responsible for executing the assertion and throwing AssertionError on
     *                  failure (don't use SoftAssert).
     */
    @Step
    public static void until(ThrowingRunnable assertion) {
        waitAndAssert(assertion, 0);
    }

    /**
     * @param assertion – the supplier that is responsible for executing the assertion and throwing AssertionError on
     *                  failure (don't use SoftAssert).
     */
    @Step
    public static void waitAndAssert(ThrowingRunnable assertion, long pollDelayInSeconds) {
        await().atMost(DEFAULT_TIMEOUT, SECONDS)
                .pollDelay(pollDelayInSeconds, SECONDS)
                .pollInSameThread()
                .pollInterval(DEFAULT_POLL_INTERVAL, MILLISECONDS)
                .untilAsserted(assertion);
    }

    @Step
    public static boolean isTrueAfterWaiting(Callable<Boolean> condition, long timeoutInSeconds, long pollingInMillis) {
        try {
            await().atMost(timeoutInSeconds, SECONDS)
                    .pollInSameThread()
                    .pollInterval(pollingInMillis, MILLISECONDS)
                    .until(condition);
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    @Step
    public static void sleep(int seconds) {
        log.info("Sleeping for [" + seconds + "] seconds");
        await().atMost(seconds + 1L, SECONDS)
                .pollDelay(seconds, SECONDS)
                .until(() -> true);
    }

    @Step
    public static void sleep(long milliseconds) {
        log.info("Sleeping for [" + milliseconds + "] milliseconds");
        await().atMost(milliseconds + 100L, MILLISECONDS)
                .pollDelay(milliseconds, MILLISECONDS)
                .until(() -> true);
    }
}
