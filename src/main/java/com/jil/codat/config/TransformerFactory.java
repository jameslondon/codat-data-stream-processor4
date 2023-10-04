package com.jil.codat.config;

import com.jil.codat.config.TransformationStrategy;
import com.jil.codat.topicMessageTransformer.*;
import io.atlasmap.api.AtlasContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransformerFactory {
    @Autowired
    private AtlasContextFactory atlasContextFactory;
    @SuppressWarnings("unchecked")
    public <I, O> MessageTransformer<I, O> createTransformer(TransformationStrategy transformationStrategy) {
        switch (transformationStrategy.getTransformer()) {
            case "JoltTransformer":
                // JoltTransformer works on String types A and B via Jolt
                return (MessageTransformer<I, O>) new JoltTransformer(transformationStrategy.getSpec());
            case "AtlasMapTransformer":
                // AtlasMapTransformer works on String types A and B via AtlasMap
                // Pass the AtlasContextFactory to the AtlasMapTransformer
                return (MessageTransformer<I, O>) new AtlasMapTransformer(transformationStrategy.getSpec(), atlasContextFactory);

            case "AccountsToAccounts_CoBaTransformer":
                // This transformer works on AccountsMessage to Accounts_CobaMessage transformation
                return (MessageTransformer<I, O>) new AccountsToAccounts_CoBaTransformer();

            case "InvoicesToInvoices_CoBaTransformer":
                // This transformer works on InvoicesMessages to Invoices_CoBaMessage transformation
                return (MessageTransformer<I, O>) new InvoicesToInvoices_CoBaTransformer();
            case "AccountsToAccounts_BQTransformer":
                // This transformer works on AccountsMessage to Accounts_BQMessage transformation
                return (MessageTransformer<I, O>) new AccountsToAccounts_BQTransformer();

            default:
                throw new IllegalArgumentException("Unknown transformation strategy: " + transformationStrategy.getTransformer());
        }
    }
}
