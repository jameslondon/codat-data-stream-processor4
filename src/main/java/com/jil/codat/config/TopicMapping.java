package com.jil.codat.config;

import lombok.Data;

@Data
public class TopicMapping {
    private String produceTopic;
    private TransformationStrategy transformationStrategy;
}
