package cn.atomicer.chopsticks.common;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Json tools
 *
 * @author raomengnan
 *         on 16-9-12.
 */

public class JsonUtils {

    private JsonUtils() {
    }

    /**
     * Deserialize the specified Json into an object of the specified class
     *
     * @param jsonStr the specified Json string
     * @param clazz   the class of T
     * @param <T>     the type of target object
     * @return object instance of T
     */
    public static <T> T mapToBean(String jsonStr, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, clazz);
    }

    /**
     * Deserialize the specified Json element into an object of the specified class
     *
     * @param e     the specified Json element
     * @param clazz the class of T
     * @param <T>   the type of target object
     * @return object instance of T
     */
    public static <T> T mapToBean(JsonElement e, Class<T> clazz) {
        return new Gson().fromJson(e, clazz);
    }

    /**
     * Converts the specified Json into a list that composed by String, Number or Map
     * eg. {@code [{"a": 1}, {"b": 2}] -> List[Map{"a": 1}, Map{"b": 2}]}
     *
     * @param jsonStr the specified Json string
     * @return object list
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

    /**
     * Read Json from the specified file into an object of the specified class
     *
     * @param filePath json file
     * @param clazz    the class of T
     * @param <T>      the type of target object
     * @return object instance of T
     * @throws IOException file read exception
     */
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

    /**
     * Converts the object into json element
     *
     * @param o the specified object
     * @return json element
     */
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

    /**
     * Converts the object into json element
     *
     * @param o json string or List object
     * @return {@link JsonArray}
     */
    public static JsonArray getJsonArray(Object o) {
        String json = new Gson().toJson(o);
        JsonElement element = new JsonParser().parse(json);
        return element.getAsJsonArray();
    }

}
