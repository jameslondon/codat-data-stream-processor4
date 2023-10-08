package com.jil.codat.customJoltOperation;


import com.bazaarvoice.jolt.Transform;
import com.bazaarvoice.jolt.SpecDriven;

import javax.inject.Inject;
import java.util.Map;
public class CustomAddTimeStamp implements SpecDriven, Transform {

    private final Object spec;

    public CustomAddTimeStamp() {
        this.spec = null;
    }

    @Inject
    public CustomAddTimeStamp(Object spec) {
        this.spec = spec;
    }

    @Override
    public Object transform(Object input) {
        processMap((Map<String, Object>) input, (Map<String, Object>) spec);
        return input;
    }

    private void processMap(Map<String, Object> inputMap, Map<String, Object> specMap) {
        for (String key : specMap.keySet()) {
            Object specVal = specMap.get(key);
            Object inputVal = inputMap.get(key);

            // Check if current spec entry is a Map
            if (specVal instanceof Map) {
                // If the current spec entry wants to insert a timestamp
                if (((Map) specVal).containsKey("timeStamp") && "true".equalsIgnoreCase(((Map) specVal).get("timeStamp").toString())) {
                    long timestamp = System.currentTimeMillis();
                    if (inputVal instanceof Map) {
                        ((Map) inputVal).put(((Map) specVal).get("key").toString(), timestamp);
                    } else {
                        inputMap.put(((Map) specVal).get("key").toString(), timestamp); // Add timestamp at current level if inputVal is not a map
                    }
                } else if (inputVal instanceof Map) {
                    // Otherwise, if the input has a nested Map at the current key, recurse
                    processMap((Map<String, Object>) inputVal, (Map<String, Object>) specVal);
                }
            }
        }

        // Specifically for root level timestamp
        if (specMap.containsKey("timeStamp") && "true".equalsIgnoreCase(specMap.get("timeStamp").toString())) {
            long timestamp = System.currentTimeMillis();
            inputMap.put(specMap.get("key").toString(), timestamp);
        }
    }

}

