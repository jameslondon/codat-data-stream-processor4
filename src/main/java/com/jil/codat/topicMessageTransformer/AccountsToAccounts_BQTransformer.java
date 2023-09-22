package com.jil.codat.topicMessageTransformer;

import com.jil.codat.TopicMessage.AccountsMessage;
import com.jil.codat.TopicMessage.Accounts_BQMessage;

import java.util.UUID;

public class AccountsToAccounts_BQTransformer implements MessageTransformer<AccountsMessage, Accounts_BQMessage> {
    @Override
    public Accounts_BQMessage transform(AccountsMessage input) {
        Accounts_BQMessage flattenedMessage = new Accounts_BQMessage();
        // Here you can add transformation logic, for now, just using the input value
        UUID uuid = UUID.randomUUID();
        flattenedMessage.setId(UUID.randomUUID());
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
    public Class<Accounts_BQMessage> getOutputType() {
        return Accounts_BQMessage.class;
    }
}
