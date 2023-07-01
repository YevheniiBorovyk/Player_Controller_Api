package com.interview.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateTimeUtils {

    public static String getCurrentDateTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(Calendar.getInstance()
                .getTime());
    }

    public static String getCurrentDateTime(String format, String timezone) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.format(new Date());
    }
}
