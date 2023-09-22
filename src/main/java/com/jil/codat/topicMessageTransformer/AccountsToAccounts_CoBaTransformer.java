package com.jil.codat.topicMessageTransformer;

import com.jil.codat.TopicMessage.AccountsMessage;
import com.jil.codat.TopicMessage.Accounts_CobaMessage;

import java.util.Map;
import java.util.UUID;

public class AccountsToAccounts_CoBaTransformer implements MessageTransformer<AccountsMessage, Accounts_CobaMessage> {
    @Override
    public Accounts_CobaMessage transform(AccountsMessage input) {
        Accounts_CobaMessage flattenedMessage = new Accounts_CobaMessage();
        // Here you can add transformation logic, for now, just using the input value
        UUID uuid = UUID.randomUUID();
        flattenedMessage.setId(UUID.randomUUID());
        Map<String, Object> account = input.getAccount();
        flattenedMessage.setAccounts(input.getAccounts());
//        flattenedMessage.setCallbackUrl(input.getCallbackUrl());
        flattenedMessage.setCompanyId(input.getCompanyId());
        flattenedMessage.setExtraField("extra field value");
        return flattenedMessage;
    }

    @Override
    public Class<AccountsMessage> getInputType() {
        return AccountsMessage.class;
    }

    @Override
    public Class<Accounts_CobaMessage> getOutputType() {
        return Accounts_CobaMessage.class;
    }
}
