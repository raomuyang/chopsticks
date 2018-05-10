package cn.atomicer.chopsticks.concurrent;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rao Mengnan
 *         on 2018/5/10.
 */
public class DynamicSemaphoreTest {
    @Test
    public void testChangePermits() throws Exception {
        DynamicSemaphore semaphore = new DynamicSemaphore(1);
        semaphore.acquire();
        assertFalse(semaphore.tryAcquire());
        assertEquals(1, semaphore.getPermits());

        semaphore.changePermits(5);
        assertEquals(5, semaphore.getPermits());
        assertTrue(semaphore.tryAcquire(4));

        semaphore.changePermits(1);
        assertEquals(1, semaphore.getPermits());
        assertFalse(semaphore.tryAcquire());

        semaphore.release(5);
        assertFalse(semaphore.tryAcquire(2));
        assertTrue(semaphore.tryAcquire());

    }

}