package cn.atomicer.chopsticks.reflect;

import cn.atomicer.chopsticks.common.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rao-Mengnan
 * on 2017/10/23.
 */
public class ClassUtil {

    private static final String UTILS_CLASS = "Date|List|Map";
    private static final Map<String, Class> CLASS_MAP;

    static {
        CLASS_MAP = new HashMap<>();
        CLASS_MAP.put("int[]", int[].class);
        CLASS_MAP.put("double[]", double[].class);
        CLASS_MAP.put("float[]", float[].class);
        CLASS_MAP.put("short", short[].class);
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
     * make object coerced into target class
     *
     * @param o     src object
     * @param clazz Target class
     * @return new object (T)
     */
    @SuppressWarnings("unchecked")
    public static <T> T conversion(Object o, Class<T> clazz) {
        if (clazz.isAssignableFrom(o.getClass())) return (T) o;
        return JsonUtils.mapToBean(o.toString(), clazz);
    }

    /**
     * @param type        name of class. If it is the base type,
     *                    you can get class via long name or short name:
     *                    type: "String" or type: "java.lang.String"
     * @param classLoader default: jvm default class loader
     * @return target class
     * @throws ClassNotFoundException no such class
     */
    public static Class str2Class(String type, ClassLoader classLoader) throws ClassNotFoundException {
        if (type == null) {
            return String.class;
        }
        if (classLoader == null) classLoader = ClassUtil.class.getClassLoader();
        type = format(type);
        try {
            Class clazz;
            if (type.contains("["))
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
     * Java base type
     *
     * @param type {@link java.lang} {@link java.util.Date}
     * @return {@link Number} or {@link java.util.Date}
     */
    private static String format(String type) {
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
