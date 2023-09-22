package com.jil.codat.topicMessageTransformer;

import org.springframework.stereotype.Service;

@Service
public interface MessageTransformer<I, O> {

    O transform(I input);

    Class<I> getInputType();

    Class<O> getOutputType();
}
