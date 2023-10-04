package com.jil.codat.config;

import io.atlasmap.api.AtlasContextFactory;
import io.atlasmap.core.DefaultAtlasContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtlasMapConfig {

    @Bean
    public AtlasContextFactory atlasContextFactory() {
        return DefaultAtlasContextFactory.getInstance();
    }
}
