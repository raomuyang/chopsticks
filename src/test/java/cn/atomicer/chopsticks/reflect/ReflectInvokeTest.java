package cn.atomicer.chopsticks.reflect;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/3/4.
 */
public class ReflectInvokeTest {
    @Test
    public void invokeTest() throws Exception {
        String str = "abcdef";
        String c = "c";
        int o = (int) ReflectInvoke.invoke(String.class, str, "indexOf", c);
        assertEquals(str.indexOf(c), o);

        assertEquals(String.valueOf(o), ReflectInvoke.invoke(String.class, null, "valueOf", o));
    }

}