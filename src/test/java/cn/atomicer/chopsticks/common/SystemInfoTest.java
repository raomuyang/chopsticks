package cn.atomicer.chopsticks.common;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/28.
 */
public class SystemInfoTest {
    @Test
    public void test() throws Exception {
        String summery = SystemDescription.summary();
        assertTrue(summery.contains("javaVersion"));
        assertTrue(summery.contains("country"));
        assertTrue(summery.contains("arch"));
        assertTrue(summery.contains("os"));
        assertTrue(summery.contains("osVersion"));
        assertTrue(summery.contains("language"));

    }

}