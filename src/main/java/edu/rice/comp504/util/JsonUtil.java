package edu.rice.comp504.util;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import com.google.gson.Gson;

public class JsonUtil {

    /**
     * Load Json from file.
     * @param path File path.
     * @return A map of the Json object.
     */
    public static Map<?, ?> loadJsonFile(String path) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path));
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
