package com.jil.codat.config;

import lombok.Data;

import java.util.List;

@Data
public class TopicConfig {
    private String consumeTopic;
    private List<TopicMapping> mappings;
}


