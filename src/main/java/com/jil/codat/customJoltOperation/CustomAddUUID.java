package com.jil.codat.customJoltOperation;

import com.bazaarvoice.jolt.SpecDriven;
import com.bazaarvoice.jolt.Transform;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class CustomAddUUID implements SpecDriven, Transform {

    private final Object spec;

    public CustomAddUUID() {
        this.spec = null;
    }

    @Inject
    public CustomAddUUID(Object spec) {
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
                if (((Map) specVal).containsKey("UUID") && "true".equalsIgnoreCase(((Map) specVal).get("UUID").toString())) {
                    String uuid = UUID.randomUUID().toString();
                    if (inputVal instanceof Map) {
                        ((Map) inputVal).put(((Map) specVal).get("key").toString(), uuid);
                    } else {
                        inputMap.put(((Map) specVal).get("key").toString(), uuid); // Add uuid at current level if inputVal is not a map
                    }
                } else if (inputVal instanceof Map) {
                    // Otherwise, if the input has a nested Map at the current key, recurse
                    processMap((Map<String, Object>) inputVal, (Map<String, Object>) specVal);
                }
            }
        }

        // Specifically for root level timestamp
        if (specMap.containsKey("UUID") && "true".equalsIgnoreCase(specMap.get("UUID").toString())) {
            String uuid = UUID.randomUUID().toString();
            inputMap.put(specMap.get("key").toString(), uuid);
        }
    }

}
