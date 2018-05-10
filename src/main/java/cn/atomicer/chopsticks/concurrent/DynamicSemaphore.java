package cn.atomicer.chopsticks.concurrent;

import java.util.concurrent.Semaphore;

/**
 * A modifiable counting semaphore.
 *
 * @author Rao Mengnan
 *         on 2018/5/10.
 *
 */
public class DynamicSemaphore extends Semaphore {
    private volatile int permits;

    public DynamicSemaphore(int permits) {
        super(permits);
        this.permits = permits;
    }

    public DynamicSemaphore(int permits, boolean fair) {
        super(permits, fair);
        this.permits = permits;
    }

    public synchronized void changePermits(int permits) {
        if (this.permits > permits && permits > 0) {
            int reduction = this.permits - permits;
            this.reducePermits(reduction);
            this.permits = permits;
        } else if (this.permits < permits) {
            int increment = permits - this.permits;
            this.release(increment);
            this.permits = permits;
        }
    }

    public int getPermits() {
        return permits;
    }
}
