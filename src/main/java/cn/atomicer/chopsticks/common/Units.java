package cn.atomicer.chopsticks.common;

/**
 * The unit tools
 *
 * @author rao-mengnan
 *         on 2017/7/20.
 */
public class Units {

    /**
     * Converts bytes to string with human readable units,
     * format with {@code String.format("%.2f %2s", size, unit)}
     *
     * @param bytes how many bytes
     * @return converted
     */
    public static String formatBufferSize(double bytes) {
        String unit = "B";
        double size = bytes;
        int times = 0;
        if (size / 0 == size) {
            size = 0;
        } else {
            while ((int) size / 1024 >= 1) {
                size /= 1024;
                times++;
            }
            switch (times) {
                case 1:
                    unit = "KB";
                    break;
                case 2:
                    unit = "MB";
                    break;
                case 3:
                    unit = "GB";
                    break;
                default:
                    break;
            }
        }

        return String.format("%.2f %2s", size, unit);
    }
}
