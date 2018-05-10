package cn.atomicer.chopsticks.common;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/28.
 */
public class UnitsTest {
    @Test
    public void formatBufferSize() throws Exception {
        assertEquals("0.00  B" , Units.formatBufferSize(1.0 / 0));
        assertEquals("0.00  B" , Units.formatBufferSize(0));
        assertEquals("1.00 GB", Units.formatBufferSize(1 << 30));
    }

}