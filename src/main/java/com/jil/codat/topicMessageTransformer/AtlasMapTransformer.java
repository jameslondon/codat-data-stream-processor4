package com.jil.codat.topicMessageTransformer;

import io.atlasmap.api.AtlasContext;
import io.atlasmap.api.AtlasContextFactory;
import io.atlasmap.api.AtlasException;
import io.atlasmap.api.AtlasSession;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class AtlasMapTransformer implements MessageTransformer<String, String> {

    private final AtlasContext context;

    public AtlasMapTransformer(String specFile, AtlasContextFactory atlasContextFactory) {
        log.info("Initializing AtlasMapTransformer with specFile: " + specFile);

        // Check if the specFile starts with "file:"
        if (specFile.startsWith("file:")) {
            specFile = specFile.substring(5); // Remove "file:" prefix
        }

        Path path = Paths.get(specFile);
        if (!Files.exists(path)) {
            log.error("Spec file " + specFile + " doesn't exist.");
            throw new RuntimeException("Spec file " + specFile + " doesn't exist.");
        }

        try {
            context = atlasContextFactory.createContext(new File(specFile));
            log.info("Atlas context created successfully.");
        } catch (AtlasException e) {
            log.error("Error during Atlas context creation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String transform(String inputJson) {
        log.info("Input JSON: " + inputJson);
//        try {
//            Thread.sleep(1000 * 60);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        AtlasSession session;
        String firstSourceKey = null;
        String firstTargetKey = null;
        try {
            session = context.createSession();
            if (!session.getSourceDocumentMap().isEmpty()) {
                firstSourceKey = session.getSourceDocumentMap().entrySet().iterator().next().getKey();
                log.info("First value of the getSourceDocumentMap: " + firstSourceKey);
            }
            session.setSourceDocument(firstSourceKey, inputJson);
            log.info("getSourceDocumentMap: " + session.getSourceDocumentMap());
            context.process(session);
        }
        catch (AtlasException e) {
            log.error("Error during transformation", e);
            throw new RuntimeException("Failed to Atlas process/transform: ", e);
        }

        if (!session.getSourceDocumentMap().isEmpty()) {
            firstTargetKey = session.getTargetDocumentMap().entrySet().iterator().next().getKey();
            log.info("First value of the getSourceDocumentMap: " + firstTargetKey);
        }

        Object targetDocument = session.getTargetDocument(firstTargetKey);
        log.info("Target getTargetDocumentMap: " + session.getTargetDocumentMap());
        return targetDocument != null ? targetDocument.toString() : null;

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
