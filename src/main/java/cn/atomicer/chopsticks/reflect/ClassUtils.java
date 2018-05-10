package cn.atomicer.chopsticks.reflect;

import cn.atomicer.chopsticks.common.JsonTool;

import java.util.HashMap;
import java.util.Map;

/**
 * Class conversion tools
 *
 * @author Rao Mengnan
 *         on 2017/10/23.
 */
public class ClassUtils {

    private static final String UTILS_CLASS = "Date|List|Map";
    static final Map<String, Class> CLASS_MAP;

    static {
        CLASS_MAP = new HashMap<>();
        CLASS_MAP.put("int[]", int[].class);
        CLASS_MAP.put("double[]", double[].class);
        CLASS_MAP.put("float[]", float[].class);
        CLASS_MAP.put("short[]", short[].class);
        CLASS_MAP.put("byte[]", byte[].class);
        CLASS_MAP.put("char[]", char[].class);
        CLASS_MAP.put("Integer[]", Integer[].class);
        CLASS_MAP.put("Double[]", Double[].class);
        CLASS_MAP.put("Float[]", Float[].class);
        CLASS_MAP.put("Short[]", Short[].class);
        CLASS_MAP.put("Byte[]", Byte[].class);
        CLASS_MAP.put("Character[]", Character[].class);
        CLASS_MAP.put("Boolean[]", Boolean[].class);
    }


    /**
     * Make object coerced into target class
     *
     * @param o     src object
     * @param clazz class of T
     * @param <T>   the type of the desired object
     * @return new object (T)
     */
    @SuppressWarnings("unchecked")
    public static <T> T conversion(Object o, Class<T> clazz) {
        if (clazz.isAssignableFrom(o.getClass())) return (T) o;
        return JsonTool.mapToBean(o.toString(), clazz);
    }

    /**
     * Get class of type name from the string, via default class loader
     *
     * @param type type name
     * @return class of type
     * @throws ClassNotFoundException not found this class
     */
    public static Class str2Class(String type) throws ClassNotFoundException {
        return str2Class(type, null);
    }

    /**
     * Get class of type name from the string, via custom class loader
     *
     * @param type        name of class. If it is the base type,
     *                    you can get class via long name or short name:
     *                    type: "String" or type: "java.lang.String"
     * @param classLoader default: jvm default class loader
     * @return target class
     * @throws ClassNotFoundException not found this class
     */
    public static Class str2Class(String type, ClassLoader classLoader) throws ClassNotFoundException {
        if (type == null) {
            return String.class;
        }
        if (classLoader == null) classLoader = ClassUtils.class.getClassLoader();
        type = format(type);
        try {
            Class clazz;
            if (type.endsWith("]"))
                clazz = CLASS_MAP.get(type);
            else
                clazz = classLoader.loadClass(type);
            if (clazz == null)
                throw new ClassNotFoundException(String.format("No such class [%s]", type));
            return clazz;
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(String.format("Param type error: (%s), class not found", type), e);
        } catch (Throwable throwable) {
            throw new RuntimeException("Get class failed: " + throwable.getMessage(), throwable);
        }

    }

    /**
     * Base types of java
     *
     * @param type {@link java.lang} {@link java.util.Date}
     * @return {@link Number} or {@link java.util.Date}
     */
    static String format(String type) {
        if (type.contains(".") || type.contains("["))
            return type;

        type = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase();
        if (UTILS_CLASS.contains(type) && !type.contains("|"))
            return "java.util." + type;
        if (type.equals("Int")) {
            type = "Integer";
        }
        return "java.lang." + type;
    }
}
