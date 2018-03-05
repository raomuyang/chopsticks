package cn.atomicer.chopsticks.common;

/**
 * @author Rao Mengnan
 *         on 2018/2/27.
 */
public class AssertUtils {

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }

    public static void assertNotNull(Object obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
