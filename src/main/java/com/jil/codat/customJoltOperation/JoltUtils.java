package com.jil.codat.customJoltOperation;

import java.util.HashMap;
import java.util.Map;

public class JoltUtils {

    @SuppressWarnings("unchecked")
    public static Object getDeepValue(String[] path, Map<String, Object> inputMap) {
        Map<String, Object> currentMap = inputMap;
        for (int i = 0; i < path.length - 1; i++) {
            currentMap = (Map<String, Object>) currentMap.get(path[i]);
            if (currentMap == null) {
                return null;
            }
        }
        return currentMap.get(path[path.length - 1]);
    }

    @SuppressWarnings("unchecked")
    public static void setDeepValue(String[] path, Object value, Map<String, Object> inputMap) {
        Map<String, Object> currentMap = inputMap;
        for (int i = 0; i < path.length - 1; i++) {
            currentMap = (Map<String, Object>) currentMap.computeIfAbsent(path[i], k -> new HashMap<>());
        }
        currentMap.put(path[path.length - 1], value);
    }
}
