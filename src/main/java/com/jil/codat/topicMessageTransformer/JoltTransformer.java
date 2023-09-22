package com.jil.codat.topicMessageTransformer;

import com.bazaarvoice.jolt.Chainr;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JoltTransformer implements MessageTransformer<String, String> {
    private final Chainr chainrSpec;
    private final Gson gson;

    public JoltTransformer(String specFile) {
//        gson = new GsonBuilder().setPrettyPrinting().create();
        gson = new Gson();


        // Check if the specFile starts with "file:"
        if (specFile.startsWith("file:")) {
            specFile = specFile.substring(5); // Remove "file:" prefix
        }

        Path path = Paths.get(specFile);
        if (!Files.exists(path)) {
            throw new RuntimeException("Spec file " + specFile + " doesn't exist.");
        }
        String fileContent;
        try {
            fileContent = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Spec file content: " + fileContent);

        // Load the Jolt specification from the external file
        try (InputStream specStream = new FileInputStream(specFile)) {
            // Parsing the content into a JSON element
            JsonElement jsonElement;
            try {
                jsonElement = JsonParser.parseReader(new InputStreamReader(specStream, StandardCharsets.UTF_8));
            } catch (JsonSyntaxException e) {
                throw new RuntimeException("Invalid JSON content in the spec file: " + specFile, e);
            }

            // Check if the parsed content is a JSON array
            if (!jsonElement.isJsonArray()) {
                throw new RuntimeException("Jolt specification in file " + specFile + " must be a JSON array.");
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Type listType = new TypeToken<List<Object>>() {
            }.getType();
            List<Object> specList = gson.fromJson(jsonArray, listType);
            chainrSpec = Chainr.fromSpec(specList);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Jolt specification from the file: " + specFile, e);
        }
    }

    @Override
    public String transform(String inputJson) {
        System.out.println("Input JSON string: " + inputJson);
        Object input = gson.fromJson(inputJson, Object.class);
        Object transformedOutput = chainrSpec.transform(input);
        return gson.toJson(transformedOutput);
    }

    @Override
    public Class<String> getInputType() {
        return String.class;
    }

    @Override
    public Class<String> getOutputType() {
        return String.class;
    }
}
