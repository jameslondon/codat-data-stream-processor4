package com.jil.codat.TopicMessage;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class Accounts_BQMessage {
    private UUID id;
    private UUID companyId;
    private String extraField;
    private Map<String, Object> account;
    private List<Map<String, Object>> accounts;
    private String callbackUrl;
}
