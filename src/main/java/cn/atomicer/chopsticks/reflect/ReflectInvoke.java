package cn.atomicer.chopsticks.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过反射调用指定的方法
 * Invoke the methods specified by reflection
 *
 * @author Rao Mengnan
 *         on 17-1-10.
 */
public class ReflectInvoke {

    private static Map<Class, Class> classMap;

    static {
        classMap = new HashMap<>();
        classMap.put(Integer.class, int.class);
        classMap.put(Double.class, double.class);
        classMap.put(Float.class, float.class);
        classMap.put(Short.class, short.class);
        classMap.put(Byte.class, byte.class);
        classMap.put(Character.class, char.class);
        classMap.put(int.class, Integer.class);
        classMap.put(double.class, Double.class);
        classMap.put(float.class, Float.class);
        classMap.put(byte.class, Short.class);
        classMap.put(short.class, Byte.class);
        classMap.put(char.class, Character.class);
        classMap.put(boolean.class, Boolean.class);
    }

    private ReflectInvoke() {
    }

    /**
     * 调用无参方法
     * Invoke non parameter method
     *
     * @param clazz      调用的类    target class
     * @param instance   调用的对象  instance of clazz
     * @param methodName 调用方法名  method of clazz
     * @return 调用结果  result of invoke
     * @throws NoSuchMethodException     没有此方法或者该方法有参数
     *                                   no such method or the method should have parameters
     * @throws InvocationTargetException an exception has occurred during perform this method
     * @throws IllegalAccessException    access to private or protected methods
     */
    @SuppressWarnings("unchecked")
    public static Object invoke(Class clazz, Object instance, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return clazz.getMethod(methodName).invoke(instance);
    }

    /**
     * @param clazz      目标类
     *                   target class
     * @param instance   目标类对象
     *                   target class object
     * @param methodName 调用方法名
     *                   the method name of invoke
     * @param args       目标方法的参数，当目标方法的参数有多个时，必须以 new Object[]{param1, param2, param3...}的形式传入
     *                   注意目标方法的参数为基本类型的数组或可变长参数时，务必不要与包装类型数组混用,
     *                   否则会抛出NoSuchMethodException的异常
     *                   The target method's parameters, when the target method has multiple parameters, must be in the form of new Object [] {param1, param2, param3 ...}
     *                   Note that when the argument of the target method is an array of primitive types or a variable length argument, be sure not to mix it with an array of wrapper types,
     *                   otherwise a NoSuchMethodException will be thrown
     * @return 调用结果  result of invoke
     * @throws NoSuchMethodException     method not found
     * @throws InvocationTargetException an exception has occurred during perform this method
     * @throws IllegalAccessException    access to private or protected methods
     */
    @SuppressWarnings("unchecked")
    public static Object invoke(Class clazz, Object instance, String methodName, Object... args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class[] argClasses1 = new Class[]{};
        Class[] argClasses2 = new Class[]{};
        if (args != null) {
            argClasses1 = new Class[args.length];
            argClasses2 = new Class[args.length];

            for (int i = 0; i < args.length; i++) {
                Class cla = args[i].getClass();
                argClasses1[i] = cla;
                argClasses2[i] = cla;

                caseType(cla, argClasses2, i);
            }
        }

        try {
            Method m = clazz.getMethod(methodName, argClasses1);
            return m.invoke(instance, args);
        } catch (NoSuchMethodException e) {
            Method m = clazz.getMethod(methodName, argClasses2);
            return m.invoke(instance, args);
        }
    }

    private static void caseType(Class cla, Class[] argClasses2, int position) {

        if (List.class.isAssignableFrom(cla)) {
            argClasses2[position] = List.class;
            return;
        }

        if (Map.class.isAssignableFrom(cla)) {
            argClasses2[position] = Map.class;
            return;
        }

        if ("Boolean".equals(cla.getSimpleName())) {
            argClasses2[position] = boolean.class;
        }
        Class cla2 = classMap.get(cla);
        if (cla2 != null)
            argClasses2[position] = cla2;
    }

}
