package com.jil.codat.config;

import lombok.Data;

@Data
public class TransformationStrategy {
    private String transformer;
    private String spec;
    private Filter filter;
}