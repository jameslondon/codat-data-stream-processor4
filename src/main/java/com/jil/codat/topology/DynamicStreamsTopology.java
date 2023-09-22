package com.jil.codat.topology;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jil.codat.config.*;
import com.jil.codat.exceptionhandler.StreamsProcessorCustomErrorHandler;
import com.jil.codat.topicMessageTransformer.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DynamicStreamsTopology {
    private final ObjectMapper objectMapper;
    private final List<TopicConfig> topics;
    private final TransformerFactory transformerFactory;
    private final StreamsProcessorCustomErrorHandler errorHandler;

    @Autowired
    public DynamicStreamsTopology(ObjectMapper objectMapper, CodatDataStreamsProperties configProperties, TransformerFactory transformerFactory, StreamsProcessorCustomErrorHandler errorHandler) {
        this.objectMapper = objectMapper;
        this.topics = configProperties.getTopics();
        this.transformerFactory = transformerFactory;
        this.errorHandler = errorHandler;
    }

    @Autowired
    public void process(StreamsBuilder streamsBuilder) {
        for (TopicConfig config : topics) {
            String consumeTopic = config.getConsumeTopic();
            for (TopicMapping mapping : config.getMappings()) {
                try {
                    processTopicMapping(streamsBuilder, consumeTopic, mapping);
                } catch (Exception e) {
                    errorHandler.handle(e);
                }
            }
        }
    }

    private void processTopicMapping(StreamsBuilder streamsBuilder, String consumeTopic, TopicMapping mapping) {
        log.info("Received from {}", consumeTopic);
        TransformationStrategy strategy = mapping.getTransformationStrategy();
        log.info("Transformation Strategy {}", strategy.getSpec());

        MessageTransformer<Object, Object> transformer = transformerFactory.createTransformer(strategy);
        processStream(streamsBuilder, consumeTopic, mapping.getProduceTopic(), transformer);
    }

    private <I> Serde<I> prepareInStreamSerdes(MessageTransformer<I, ?> transformer) {
        Serializer<I> valueSerializer;
        Deserializer<I> valueDeserializer;

        if (transformer.getInputType().equals(String.class)) {
            valueDeserializer = (Deserializer<I>) Serdes.String().deserializer();
            valueSerializer = (Serializer<I>) Serdes.String().serializer();
        } else {
            JsonSerde<I> jsonSerde = new JsonSerde<>(transformer.getInputType(), objectMapper);
            valueDeserializer = jsonSerde.deserializer();
            valueSerializer = jsonSerde.serializer();
        }

        return Serdes.serdeFrom(valueSerializer, valueDeserializer);
    }

    private <O> Serde<O> prepareOutStreamSerdes(MessageTransformer<?, O> transformer) {
        if (transformer.getOutputType().equals(String.class)) {
            return (Serde<O>) Serdes.String();
        } else {
            return new JsonSerde<>(transformer.getOutputType(), objectMapper);
        }
    }

    private <I, O> void processStream(StreamsBuilder streamsBuilder, String inputTopic, String outputTopic, MessageTransformer<I, O> transformer) {
        Serde<String> keySerde = Serdes.String();
        Serde<I> valueSerde = prepareInStreamSerdes(transformer);

        KStream<String, I> inputStream = streamsBuilder.stream(inputTopic, Consumed.with(keySerde, valueSerde));

        KStream<String, O>  outputStream = inputStream
                .peek((key, value) -> log.info("Input Topic: {}. Before transformation - Key: {}, Value: {}", inputTopic, key, value))
                .mapValues(value -> {
                    O transformedValue = transformer.transform(value);
                    return transformedValue;
                })
                .peek((key, value) -> log.info("Output Topic: {}. After transformation - Key: {}, Value: {}", outputTopic, key, value));

        outputStream.to(outputTopic, Produced.with(Serdes.String(), prepareOutStreamSerdes(transformer)));
    }
}
