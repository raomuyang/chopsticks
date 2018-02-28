package cn.atomicer.chopsticks.common;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/27.
 */
public class AssertUtilsTest {
    @Test
    public void isNullOrEmpty() throws Exception {
        Assert.assertTrue(AssertUtils.isNullOrEmpty(null));
    }

    @Test(expected = NullPointerException.class)
    public void assertNotNull() throws Exception {
        AssertUtils.assertNotNull(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertTrue() throws Exception {
        try {
            AssertUtils.assertTrue(true, "");
        } catch (Throwable e) {
            fail("assert true failed");
        }
        AssertUtils.assertTrue(false, "");
    }

}