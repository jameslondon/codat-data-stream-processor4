package com.jil.codat.customJoltOperation;

import com.bazaarvoice.jolt.Transform;
import com.bazaarvoice.jolt.exception.SpecException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class CustomAddTimestamp implements Transform {

    @Override
    public Object transform(Object input) {
        if (!(input instanceof Map)) {
            throw new SpecException("CustomAddUUID expected a Map input, but got: " + input.getClass().getSimpleName());
        }

        Map<String, Object> inputMap = (Map<String, Object>) input;
        String key = getKeyForValue(inputMap, "TIMESTAMP");
        if (key != null) {
            System.out.println("The key for the value 'TIMESTAMP' is: " + key);
            String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            inputMap.replace(key, sdf.format(new Date()));
        } else {
            System.out.println("The value 'TIMESTAMP' is not present in the map.");
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
