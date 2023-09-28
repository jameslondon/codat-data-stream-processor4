package com.jil.codat.customJoltOperation;

import com.bazaarvoice.jolt.Transform;
import com.bazaarvoice.jolt.exception.SpecException;

import java.util.Map;
import java.util.UUID;

public class CustomAddUUID implements Transform {

    @Override
    public Object transform(Object input) {
        if (!(input instanceof Map)) {
            throw new SpecException("CustomAddUUID expected a Map input, but got: " + input.getClass().getSimpleName());
        }

        Map<String, Object> inputMap = (Map<String, Object>) input;
        String key = getKeyForValue(inputMap, "CORRELATIONID");
        if (key != null) {
            System.out.println("The key for the value 'CORRELATIONID' is: " + key);
            inputMap.replace(key, UUID.randomUUID().toString());
        } else {
            System.out.println("The value 'CORRELATIONID' is not present in the map.");
        }
        return inputMap;
    }

    public static String getKeyForValue(Map<String, Object> map, Object value) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null; // Return null if the value is not found in the map
    }

}
