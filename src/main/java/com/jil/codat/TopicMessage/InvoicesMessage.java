package com.jil.codat.TopicMessage;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class InvoicesMessage {
    private UUID id;
    private UUID companyId;
    private String extraField;
    private Map<String, Object> invoice;
    private List<Map<String, Object>> invoices;
    private String callbackUrl;
}
