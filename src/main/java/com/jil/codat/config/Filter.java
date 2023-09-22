package com.jil.codat.config;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

@Data
public class Filter {
    private List<String> companyIds;
    private String status;
}
