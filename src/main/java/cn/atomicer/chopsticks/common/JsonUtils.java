package cn.atomicer.chopsticks.common;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raomengnan on 16-9-12.
 * Json utils
 */

public class JsonUtils {

    private JsonUtils() {
    }

    public static <T> T mapToBean(String jsonStr, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, clazz);
    }

    public static <T> T mapToBean(JsonElement e, Class<T> clazz) {
        return new Gson().fromJson(e, clazz);
    }

    /**
     * 将json转换为 String/Number/Map 数组
     * eg. [{"a": 1}, {"b": 2}] -> List[Map{"a": 1}, Map{"b": 2}]
     * @param jsonStr ["a", "b"]
     * @return List["a", "b"]
     */
    @SuppressWarnings("unchecked")
    public static List mapToList(String jsonStr) {
        List list;
        try {
            list = mapToBean(jsonStr, List.class);
        } catch (Exception e) {
            list = new ArrayList();
            list.add(jsonStr);
        }
        return list;
    }

    public static <T> T loadJsonFile(String filePath, Class<T> clazz) throws IOException {

        FileInputStream fileInputStream = null;
        try {
            Gson gson = new Gson();
            filePath = filePath.replace("%20", " ");
            fileInputStream = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(fileInputStream)
            );

            return gson.fromJson(reader, clazz);
        } finally {
            if (fileInputStream != null)
                fileInputStream.close();
        }
    }

    public static JsonElement getJsonElement(Object o) {
        if (String.class.isAssignableFrom(o.getClass()))
            return new JsonParser().parse(o.toString());
        if (JsonElement.class.equals(o.getClass()))
            return (JsonElement) o;
        if (JsonElement.class.isAssignableFrom(o.getClass()))
            return (JsonElement) o;
        String json = new Gson().toJson(o);
        return new JsonParser().parse(json);
    }

    public static JsonArray getJsonArray(Object o) {
        String json = new Gson().toJson(o);
        JsonElement element = new JsonParser().parse(json);
        return element.getAsJsonArray();
    }

}
