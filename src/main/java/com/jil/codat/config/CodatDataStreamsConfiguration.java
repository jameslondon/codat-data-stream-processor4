package com.jil.codat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jil.codat.exceptionhandler.StreamsProcessorCustomErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.streams.RecoveringDeserializationExceptionHandler;

import java.util.List;

@Configuration
@Slf4j
public class CodatDataStreamsConfiguration {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private CodatDataStreamsProperties dataStreamsProperties;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        var kafkaStreamsProperties = kafkaProperties.buildStreamsProperties();
        kafkaStreamsProperties.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
                RecoveringDeserializationExceptionHandler.class);
        kafkaStreamsProperties.put(RecoveringDeserializationExceptionHandler.KSTREAM_DESERIALIZATION_RECOVERER,
                recoverer());
        // Enable idempotence for Kafka Streams' internal producer
        kafkaStreamsProperties.put("producer.enable.idempotence", "true");

        // Increase the session timeout to 5 minutes
        kafkaStreamsProperties.put("session.timeout.ms", "300000");

        // Increase the max poll interval to 10 minutes
        kafkaStreamsProperties.put("max.poll.interval.ms", "600000");
        return new KafkaStreamsConfiguration(kafkaStreamsProperties);
    }

    @Bean
    public StreamsBuilderFactoryBeanConfigurer streamsBuilderFactoryBeanConfigurer() {
        return factoryBeanConfigurer -> {
            factoryBeanConfigurer.setStreamsUncaughtExceptionHandler(new StreamsProcessorCustomErrorHandler());
        };
    }

    private ConsumerRecordRecoverer recoverer() {
        return (consumerRecord, e) -> {
            log.error("Exception is : {} , Failed Record : {} ", consumerRecord, e.getMessage(), e);
        };
    }

//    @Bean
//    public List<NewTopic> createTopics() {
//        return dataStreamsProperties.getTopics().stream()
//                .map(topicConfig -> TopicBuilder.name(topicConfig.getProduce())
//                        .partitions(topicConfig.getPartitions())
//                        .replicas(topicConfig.getReplicas())
//                        .build())
//                .toList();
//    }
}
