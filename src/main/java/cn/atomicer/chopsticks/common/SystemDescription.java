package cn.atomicer.chopsticks.common;

/**
 * Summary of current system and jvm
 *
 * @author Rao Mengnan
 *         on 2017/8/11.
 */
public class SystemDescription {
    private static String javaRuntime;
    private static String javaVersion;
    private static String country;
    private static String arch;
    private static String os;
    private static String osShortName;
    private static String osVersion;
    private static String language;
    private static int processors;

    static {
        javaRuntime = System.getProperty("java.runtime.name");
        javaVersion = System.getProperty("java.runtime.version");
        country = System.getProperty("user.country");
        arch = System.getProperty("os.arch");
        os = System.getProperty("os.name");
        osVersion = System.getProperty("os.version");
        language = System.getProperty("user.language");
        processors = Runtime.getRuntime().availableProcessors();
        osShortName = getShortName(os);
    }

    public static String getJavaRuntime() {
        return javaRuntime;
    }

    public static String getJavaVersion() {
        return javaVersion;
    }

    public static String getCountry() {
        return country;
    }

    public static String getArch() {
        return arch;
    }

    public static String getOs() {
        return os;
    }

    public static String getLanguage() {
        return language;
    }

    public static String getOsVersion() {
        return osVersion;
    }

    public static int getProcessors() {
        return processors;
    }

    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public static String getOsShortName() {
        return osShortName;
    }

    private static String getShortName(String name) {
        name = name.toLowerCase();
        if (name.contains("mac")) {
            return "mac";
        } else if (name.contains("linux")) {
            return "linux";
        } else if (name.contains("win")) {
            return "win";
        }
        return name;
    }

    public static String summary() {
        return "SystemInfo{" +
                "javaRuntime='" + javaRuntime + '\'' +
                ", javaVersion='" + javaVersion + '\'' +
                ", country='" + country + '\'' +
                ", arch='" + arch + '\'' +
                ", os='" + os + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", language='" + language + '\'' +
                ", processors=" + processors +
                ", freeMemory=" + getFreeMemory() +
                '}';
    }

    @Override
    public String toString() {
        return summary();
    }
}
