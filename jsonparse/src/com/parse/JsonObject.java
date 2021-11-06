package com.parse;

import com.parse.Exception.JsonParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonObject {

    private Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public List<Map.Entry<String, Object>> getAllKeyValue() {
        return new ArrayList<>(map.entrySet());
    }

    public JsonObject getJsonObject(String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Invalid key");
        }
        Object obj = map.get(key);
        if (!(obj instanceof JsonObject)) {
            throw new JsonParseException("Type of value is not JsonObject");
        }
        return (JsonObject) obj;
    }

    public JsonArray getJsonArray(String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Invalid key");
        }
        Object obj = map.get(key);
        if (!(obj instanceof JsonArray)) {
            throw new JsonParseException("Type of value is not JsonArray");
        }
        return (JsonArray) obj;
    }


    @Override
    public String toString() {
        return "good job";
    }

}
