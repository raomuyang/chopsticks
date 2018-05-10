package cn.atomicer.chopsticks.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The date tools, provide date convert, date format, etc.
 *
 * @author Rao Mengnan
 *         on 2016/6/25.
 */
public class Dates {

    public static final String DEFAULT_DATE_FORMAT = "MMM d yyyy, HH:mm:ss";


    /**
     * Get the date before n days from specified date
     *
     * @param d    specified date
     * @param days days before
     * @return the date of d days ago.
     */
    public static Date getDateBeforeXDay(Date d, int days) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
        return now.getTime();
    }

    /**
     * Get the date before n days from specified timestamp
     *
     * @param d    specified date
     * @param days days before
     * @return the date of n days ago.
     */
    public static Date getDateBeforeXDay(Timestamp d, int days) {
        Date date = new Date(d.getTime());
        return getDateBeforeXDay(date, days);
    }

    /**
     * Get the date of specified date after x milliseconds
     *
     * @param d     specified date
     * @param mills milliseconds after
     * @return the date of after x milliseconds.
     */
    public static Date getDateAfter(Date d, long mills) {
        return new Date(d.getTime() + mills);
    }

    /**
     * Get the date after n days
     *
     * @param d    specified date
     * @param days days after
     * @return the date of after n days.
     */
    public static Date getDateAfterXDay(Date d, int days) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
        return now.getTime();
    }

    /**
     * Get the date after n days
     *
     * @param timestamp specified timpstamp
     * @param days      days after
     * @return the date of after n days.
     */
    public static Date getDateAfterXDay(Timestamp timestamp, int days) {
        Date date = new Date(timestamp.getTime());
        return getDateAfterXDay(date, days);
    }


    /**
     * Format date to string by specified pattern, the "locale" whose date
     * format symbols is ENGLISH
     *
     * @param date   specified date
     * @param format format pattern
     * @return formatted string
     */
    public static String formatDate(Date date, String format) {
        return formatDate(date, format, Locale.ENGLISH);
    }

    /**
     * Format date to string by specified pattern
     *
     * @param date   specified date
     * @param format format pattern
     * @param locale the locale whose date format symbols should be used
     * @return formatted string
     * @see SimpleDateFormat
     */
    public static String formatDate(Date date, String format, Locale locale) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
        return dateFormat.format(date);
    }

    public static String formatTimeStamp(long timeStamp) {
        return formatTimeStamp(timeStamp, DEFAULT_DATE_FORMAT);
    }

    public static String formatTimeStamp(long timeStamp, String format) {
        Date date = new Date(timeStamp);
        return formatDate(date, format);
    }

    /**
     * The string format of Unix timestamp time to Date or the specified format
     *
     * @param dateStr A Unix timestamp or formatted string
     * @return The Date object after conversion
     * @throws ParseException illegal date string
     */
    public static Date string2Date(String dateStr, String format) throws ParseException {
        String errorMsg = "dateStr is not a Unix timestamp or formatted date: dateStr[%s], format[%s]";
        if (format != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            try {
                return df.parse(dateStr);
            } catch (ParseException e) {
                throw new ParseException(String.format(errorMsg, dateStr, format), 0);
            }
        }
        try {
            long timestamp = (long) (Double.parseDouble(dateStr) * 1000);
            return new Date(timestamp);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(errorMsg, dateStr, format), 0);
        }
    }

    /**
     * Converts milliseconds to string with human readable unit,
     * example: 1d10h20m02s
     *
     * @return converted string
     */
    public static String totalTime(long millis) {
        String str = String.format("%.2fs", (millis / 1000.0) % 60);

        long minutes = millis / (1000 * 60);
        str = (minutes > 0 ? minutes % 60 + "m" : "") + str;
        if (minutes / 60 <= 0) {
            return str;
        }

        long hours = minutes / 60;
        return (hours / 24 > 0 ? (hours / 24) + "d" : "") + (hours % 24) + "h" + str;

    }

}
