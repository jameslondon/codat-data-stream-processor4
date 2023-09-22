package com.jil.codat.topicMessageTransformer;

import com.jil.codat.TopicMessage.InvoicesMessage;
import com.jil.codat.TopicMessage.Invoices_CobaMessage;

import java.util.UUID;

public class InvoicesToInvoices_CoBaTransformer implements MessageTransformer<InvoicesMessage, Invoices_CobaMessage> {
    @Override
    public Invoices_CobaMessage transform(InvoicesMessage input) {
        Invoices_CobaMessage flattenedMessage = new Invoices_CobaMessage();
        // Here you can add transformation logic, for now, just using the input value
        UUID uuid = UUID.randomUUID();
        flattenedMessage.setId(UUID.randomUUID());
        flattenedMessage.setInvoices(input.getInvoices());
//        flattenedMessage.setCallbackUrl(input.getCallbackUrl());
        flattenedMessage.setCompanyId(input.getCompanyId());
        flattenedMessage.setExtraField("extra invoice field value");
        return flattenedMessage;
    }
    @Override
    public Class<InvoicesMessage> getInputType() {
        return InvoicesMessage.class;
    }

    @Override
    public Class<Invoices_CobaMessage> getOutputType() {
        return Invoices_CobaMessage.class;
    }
}
