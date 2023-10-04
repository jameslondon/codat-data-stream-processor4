package com.jil.codat.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaTopicManager {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties adminClientProps = new Properties();
        adminClientProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (AdminClient adminClient = AdminClient.create(adminClientProps)) {
            // List of topics to create
            List<String> topicsToCreate = List.of(
                    // CODAT Pull with original full response payload
                    "Accounts_CODAT",
                    "Invoices_CODAT",
                    "Bills_CODAT",
                    "AccountTransactions_CODAT",
                    // transformed from the original full response payload to only data type related info
                    "Accounts",
                    "Invoices",
                    "Bills",
                    "AccountTransactions",
                    // for BQ Sink connector consumption
                    "Accounts_CODAT_BQ",
                    "Invoices_CODAT_BQ",
                    "Bills_CODAT_BQ",
                    "AccountTransactions_CODAT_BQ",
                    // for CoBa HTTP Sink connector consumption
                    "Accounts_CoBa",
                    "Accounts_CoBa2",
                    "Invoices_CoBa",
                    "Bills_CoBa",
                    "AccountTransactions_CoBa"
            );

            for (String topicName : topicsToCreate) {
                if (!doesTopicExist(adminClient, topicName)) {
                    createTopic(adminClient, topicName, 1, (short) 1);
                } else {
                    System.out.println("Topic '" + topicName + "' already exists.");
                }
            }
        }
    }

    private static boolean doesTopicExist(AdminClient adminClient, String topicName) throws InterruptedException, ExecutionException {
        DescribeTopicsResult result = adminClient.describeTopics(Collections.singletonList(topicName));
        KafkaFuture<TopicDescription> topicDescriptionFuture = result.values().get(topicName);

        try {
            // Check if the topic exists
            TopicDescription topicDescription = topicDescriptionFuture.get();
            return topicDescription != null;
        } catch (Exception e) {
            // An exception occurred, which likely means the topic does not exist
            return false;
        }
    }

    private static void createTopic(AdminClient adminClient, String topicName, int numPartitions, short replicationFactor)
            throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        adminClient.createTopics(List.of(newTopic)).all().get();
        System.out.println("Topic '" + topicName + "' created successfully.");
    }
}
