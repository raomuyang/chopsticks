package cn.atomicer.chopsticks.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String tools, provide full width character check, etc
 *
 * @author rao-mengnan
 *         on 2017/3/17.
 */
public class StringUtils {
    private static final String FULL_WIDTH_CHARACTER = "[\\uFF00-\\uFFFF]|[\\u3000-\\u303F]";
    private static final String FULL_WIDTH_CHARACTER_REG = ".*?(" + FULL_WIDTH_CHARACTER + "+).*?";
    private static final Pattern FULL_WIDTH_CHARACTERS_PATTERN = Pattern.compile(FULL_WIDTH_CHARACTER_REG);

    private static final Pattern INTEGER_PATTERN;

    static {
        INTEGER_PATTERN = Pattern.compile("^[0-9]*");
    }


    /**
     * Get full width character
     *
     * @param str target string
     * @return matcher
     */
    public static Matcher getFullWidthCharacterMatcher(String str) {
        return FULL_WIDTH_CHARACTERS_PATTERN.matcher(str);
    }

    /**
     * Is full width character existing
     *
     * @param str target string
     * @return check result
     */
    public static boolean existingFullWidthCharacters(String str) {
        return getFullWidthCharacterMatcher(str).matches();
    }

    /**
     * Replace all the full width character with specified pattern
     *
     * @param str         string to replace
     * @param replacement pattern to replace full width character
     * @return replaced string
     */
    public static String replaceAllFullWidthCharacters(String str, String replacement) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(FULL_WIDTH_CHARACTER, replacement);
    }

    /**
     * Is string can converts to integer
     *
     * @param str target string
     * @return can converts
     */
    public static boolean isInteger(String str) {
        return (str != null && str.length() > 0) && INTEGER_PATTERN.matcher(str).matches();
    }
}
