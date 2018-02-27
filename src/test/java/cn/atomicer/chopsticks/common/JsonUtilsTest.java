package cn.atomicer.chopsticks.common;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Rao-Mengnan
 * on 2018/2/27.
 */
public class JsonUtilsTest {
    static class Demo {
        private int a;
        private String b;

        public Demo(int a, String b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Demo demo = (Demo) o;

            if (a != demo.a) return false;
            return b != null ? b.equals(demo.b) : demo.b == null;
        }

        @Override
        public int hashCode() {
            int result = a;
            result = 31 * result + (b != null ? b.hashCode() : 0);
            return result;
        }
    }
    private static final Gson gson = new Gson();
    private static final Demo demo = new Demo(1, "test");

    @Test
    public void mapToBean() throws Exception {
        String json = gson.toJson(demo);
        Demo demo2 = JsonUtils.mapToBean(json, Demo.class);
        assertEquals(demo, demo2);

        JsonElement element = gson.toJsonTree(demo);
        Demo demo3 = JsonUtils.mapToBean(element, Demo.class);
        assertEquals(demo, demo3);
    }

    @Test
    public void mapToList() throws Exception {
        String json = gson.toJson(demo);
        List list = JsonUtils.mapToList(String.format("[%s, %s, %s]", json, json, 1));
        assertEquals(3, list.size());
        assertTrue(Number.class.isAssignableFrom(list.get(2).getClass()));
        Map map = JsonUtils.mapToBean(json, Map.class);
        assertEquals(map, list.get(0));
        assertEquals(map, list.get(1));
    }

    @Test
    public void loadJsonFile() throws Exception {
        File tmp = new File("test-load-json.out");
        String json = gson.toJson(demo);

        try (FileWriter writer = new FileWriter(tmp)) {
            writer.write(json);
            writer.flush();
            Demo demo2 = JsonUtils.loadJsonFile(tmp.getAbsolutePath(), Demo.class);
            assertEquals(demo, demo2);
        } finally {
            tmp.delete();
        }
    }

    @Test
    public void getJsonElement() throws Exception {
        JsonElement e1 = JsonUtils.getJsonElement(demo);

        String json = gson.toJson(demo);
        JsonElement e2 = JsonUtils.getJsonElement(json);

        JsonElement e3 = JsonUtils.getJsonElement(e2);

        assertEquals(e1, e2);
        assertEquals(e2, e3);
    }

    @Test
    public void getJsonArray() throws Exception {
        String json = gson.toJson(demo);
        List list = JsonUtils.mapToList(String.format("[%s, %s, %s]", json, json, 1));
        JsonArray array = JsonUtils.getJsonArray(list);
        assertEquals(3, array.size());

        array = JsonUtils.getJsonArray(array);
        assertEquals(3, array.size());
    }

}