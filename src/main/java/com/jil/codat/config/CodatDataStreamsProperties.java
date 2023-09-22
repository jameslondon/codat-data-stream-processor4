package com.jil.codat.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "data-stream-processor")
@Data
public class CodatDataStreamsProperties {
    @Value("${transformationStrategy.spec:#{null}}")
    private List<TopicConfig> topics;
}

