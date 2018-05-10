package cn.atomicer.chopsticks.common;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/27.
 */
public class AssertHelperTest {
    @Test
    public void isNullOrEmpty() throws Exception {
        Assert.assertTrue(AssertHelper.isNullOrEmpty(null));
    }

    @Test(expected = NullPointerException.class)
    public void assertNotNull() throws Exception {
        AssertHelper.assertNotNull(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertTrue() throws Exception {
        try {
            AssertHelper.assertTrue(true, "");
        } catch (Throwable e) {
            fail("assert true failed");
        }
        AssertHelper.assertTrue(false, "");
    }

}