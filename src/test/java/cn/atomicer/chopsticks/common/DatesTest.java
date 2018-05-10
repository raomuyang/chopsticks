package cn.atomicer.chopsticks.common;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/27.
 */
public class DatesTest {
    @Test
    public void getDateBeforeXDay() throws Exception {
        Date yesterday = Dates.getDateBeforeXDay(new Date(), 1);
        long diff = new Date().getTime() - yesterday.getTime();
        assertTrue(TimeUnit.DAYS.toMillis(1) <= diff);
        assertTrue(TimeUnit.SECONDS.toMillis(1) >= (diff - TimeUnit.DAYS.toMillis(1)));

    }

    @Test
    public void getDateBeforeXDayByTimestamp() throws Exception {
        Date yesterday = Dates.getDateBeforeXDay(new Timestamp(new Date().getTime()), 1);
        long diff = new Date().getTime() - yesterday.getTime();
        assertTrue(TimeUnit.DAYS.toMillis(1) <= diff);
        assertTrue(TimeUnit.SECONDS.toMillis(1) >= (diff - TimeUnit.DAYS.toMillis(1)));

    }

    @Test
    public void getDateAfter() throws Exception {
        Date date = new Date();
        assertEquals(date.getTime() + 1, Dates.getDateAfter(date, 1).getTime());
    }

    @Test
    public void getDateAfterXDay() throws Exception {
        Date date = new Date();
        assertEquals(date.getTime() + TimeUnit.DAYS.toMillis(1),
                Dates.getDateAfterXDay(new Timestamp(date.getTime()), 1).getTime());
    }


    @Test
    public void formatTimeStamp() throws Exception {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Dates.DEFAULT_DATE_FORMAT, Locale.ENGLISH);
        String f = Dates.formatTimeStamp(date.getTime());
        assertEquals(dateFormat.format(date), f);
    }


    @Test
    public void string2Date() throws Exception {
        Date now = new Date();
        String formatted = Dates.formatDate(now, Dates.DEFAULT_DATE_FORMAT);
        Date d1 = Dates.string2Date(formatted, Dates.DEFAULT_DATE_FORMAT);
        Date d2 = Dates.string2Date(String.valueOf(now.getTime() / 1000), null);
        assertTrue(Math.abs(now.getTime() - d1.getTime()) < TimeUnit.SECONDS.toMillis(1));
        assertTrue(Math.abs(now.getTime() - d2.getTime()) < TimeUnit.SECONDS.toMillis(1));
    }

    @Test
    public void totalTime() throws Exception {
        assertEquals("1.00s", Dates.totalTime(TimeUnit.SECONDS.toMillis(1)));
        assertEquals("1d0h0m0.00s", Dates.totalTime(TimeUnit.DAYS.toMillis(1)));
        assertEquals("1d0h1m0.00s", Dates.totalTime(TimeUnit.DAYS.toMillis(1) + TimeUnit.MINUTES.toMillis(1)));
    }

}