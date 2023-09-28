package com.jil.codat.customJoltOperation;


import com.bazaarvoice.jolt.Transform;
import com.bazaarvoice.jolt.exception.SpecException;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class CustomStringifyJson implements Transform {

    private String key;
    public CustomStringifyJson() {
        this.key = null; // or some default value
    }

    public CustomStringifyJson(String key) {
        this.key = key;
    }

    @Override
    public Object transform(Object input) {
        if (!(input instanceof Map)) {
            throw new SpecException("CustomStringifyJson expected a Map input, but got: " + input.getClass().getSimpleName());
        }

        Map<String, Object> inputMap = (Map<String, Object>) input;
        if (key == null) {
            if (!inputMap.isEmpty()) {
                key = inputMap.keySet().iterator().next();
            } else {
                throw new SpecException("Input map is empty and key is not initialized.");
            }
        }
        Object value = JoltUtils.getDeepValue(key.split("\\."), inputMap);
        if (value != null) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(value);
            JoltUtils.setDeepValue(key.split("\\."), jsonString, inputMap);
        }

        return inputMap;
    }

    public static CustomStringifyJson fromSpec(Object spec) {
        if (!(spec instanceof String)) {
            throw new SpecException("CustomStringifyJson expected a spec of type String, but got: " + spec.getClass().getSimpleName());
        }

        return new CustomStringifyJson((String) spec);
    }
}
