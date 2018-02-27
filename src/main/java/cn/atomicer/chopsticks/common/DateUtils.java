package cn.atomicer.chopsticks.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Raomengnan
 * on 2016/6/25.
 */
public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "MMM d yyyy, HH:mm:ss";


    public static Date getDateBeforeXDay(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    public static Date getDateBeforeXDay(Timestamp d, int day) {
        Date date = new Date(d.getTime());
        return getDateBeforeXDay(date, day);
    }

    public static Date getDateAfter(Date d, long mills) {
        return new Date(d.getTime() + mills);
    }

    public static Date getDateAfterXDay(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static Date getDateAfterXDay(Timestamp d, int day) {
        Date date = new Date(d.getTime());
        return getDateAfterXDay(date, day);
    }


    public static String formatDate(Date date, String format) {
        return formatDate(date, format, Locale.ENGLISH);
    }

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
     * 将字符串格式的Unix时间戳或者指定格式的时间转为Date
     *
     * @param dateStr Unix时间戳或格式化的时间字符串
     * @return 转换后的Date对象
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
     * 将毫秒转换为统计时间
     *
     * @return example: 1d10h20m02s
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
