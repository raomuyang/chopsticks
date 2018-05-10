package cn.atomicer.chopsticks.exception;

/**
 * @author Rao Mengnan
 *         on 2018/5/9.
 */
public class OnErrorNotCatcherException extends Exception {
    public OnErrorNotCatcherException() {
    }

    public OnErrorNotCatcherException(String message) {
        super(message);
    }

    public OnErrorNotCatcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public OnErrorNotCatcherException(Throwable cause) {
        super(cause);
    }

    public OnErrorNotCatcherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
