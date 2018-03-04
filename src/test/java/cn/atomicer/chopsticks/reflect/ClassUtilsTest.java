package cn.atomicer.chopsticks.reflect;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/3/1.
 */
public class ClassUtilsTest {
    @Test
    public void conversion() throws Exception {

    }

    @Test
    public void str2Class() throws Exception {
        assertEquals(Integer.class, ClassUtils.str2Class("int"));
        assertEquals(Double[].class, ClassUtils.str2Class("Double[]"));
        assertEquals(double[].class, ClassUtils.str2Class("double[]"));

        for (Map.Entry<String, Class> entry: ClassUtils.CLASS_MAP.entrySet()) {
            assertEquals(entry.getValue(), ClassUtils.str2Class(entry.getKey()));
        }

        Package p = Package.getPackage("java.lang");
        System.out.println(p);
    }

    @Test
    public void format() throws Exception {
        String name = "a.b.c";
        assertEquals(name, ClassUtils.format(name));

        name = "int[]";
        assertEquals(name, ClassUtils.format(name));

        name = "int";
        assertEquals("java.lang.Integer", ClassUtils.format(name));

        name = "date";
        assertEquals("java.util.Date", ClassUtils.format(name));

        name = "map";
        assertEquals("java.util.Map", ClassUtils.format(name));

        name = "lIst";
        assertEquals("java.util.List", ClassUtils.format(name));

    }

}