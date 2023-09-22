package com.jil.codat.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountsMockDataProducer {
    static String ACCOUNTS_RAW = "Accounts_RAW";

    public static void main(String[] args) {

        String mockAccontsPayload1 = "{\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"id\": \"0a5760aa-4003-4a5b-b26c-ccdfb904b3cc\",\n" +
                "            \"nominalCode\": \"835\",\n" +
                "            \"name\": \"Directors' Loan Account\",\n" +
                "            \"description\": \"Monies owed to or from company directors.\",\n" +
                "            \"fullyQualifiedCategory\": \"Liability.Current\",\n" +
                "            \"fullyQualifiedName\": \"Liability.Current.Directors' Loan Account\",\n" +
                "            \"currency\": \"GBP\",\n" +
                "            \"currentBalance\": 0.00,\n" +
                "            \"type\": \"Liability\",\n" +
                "            \"status\": \"Active\",\n" +
                "            \"isBankAccount\": false,\n" +
                "            \"modifiedDate\": \"2023-08-11T14:21:30Z\",\n" +
                "            \"sourceModifiedDate\": \"2022-10-19T03:39:35\",\n" +
                "            \"validDatatypeLinks\": [],\n" +
                "            \"metadata\": {\n" +
                "                \"isDeleted\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"138a8eb3-5c08-4e59-a3bc-892119694447\",\n" +
                "            \"nominalCode\": \"449\",\n" +
                "            \"name\": \"Motor Vehicle Expenses\",\n" +
                "            \"description\": \"Expenses incurred on the running of business motor vehicles.\",\n" +
                "            \"fullyQualifiedCategory\": \"Expense.Operating\",\n" +
                "            \"fullyQualifiedName\": \"Expense.Operating.Motor Vehicle Expenses\",\n" +
                "            \"currency\": \"GBP\",\n" +
                "            \"currentBalance\": 0.00,\n" +
                "            \"type\": \"Expense\",\n" +
                "            \"status\": \"Active\",\n" +
                "            \"isBankAccount\": false,\n" +
                "            \"modifiedDate\": \"2023-08-11T14:21:30Z\",\n" +
                "            \"sourceModifiedDate\": \"2023-05-23T15:33:35\",\n" +
                "            \"validDatatypeLinks\": [],\n" +
                "            \"metadata\": {\n" +
                "                \"isDeleted\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"141d2987-409d-489b-b233-062eb040b045\",\n" +
                "            \"nominalCode\": \"850\",\n" +
                "            \"name\": \"Suspense\",\n" +
                "            \"description\": \"A clearing account.\",\n" +
                "            \"fullyQualifiedCategory\": \"Liability.Current\",\n" +
                "            \"fullyQualifiedName\": \"Liability.Current.Suspense\",\n" +
                "            \"currency\": \"GBP\",\n" +
                "            \"currentBalance\": 0.00,\n" +
                "            \"type\": \"Liability\",\n" +
                "            \"status\": \"Active\",\n" +
                "            \"isBankAccount\": false,\n" +
                "            \"modifiedDate\": \"2023-08-11T14:21:30Z\",\n" +
                "            \"sourceModifiedDate\": \"2022-09-29T09:45:35\",\n" +
                "            \"validDatatypeLinks\": [],\n" +
                "            \"metadata\": {\n" +
                "                \"isDeleted\": false\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageNumber\": 2,\n" +
                "    \"pageSize\": 3,\n" +
                "    \"totalResults\": 98,\n" +
                "    \"_links\": {\n" +
                "        \"current\": {\n" +
                "            \"href\": \"/companies/dee35d3c-d866-420d-a7ec-628ab5a302b8/data/accounts?orderBy=id&pageSize=3&page=2\"\n" +
                "        },\n" +
                "        \"self\": {\n" +
                "            \"href\": \"/companies/dee35d3c-d866-420d-a7ec-628ab5a302b8/data/accounts\"\n" +
                "        },\n" +
                "        \"previous\": {\n" +
                "            \"href\": \"/companies/dee35d3c-d866-420d-a7ec-628ab5a302b8/data/accounts?orderBy=id&pageSize=3&page=1\"\n" +
                "        },\n" +
                "        \"next\": {\n" +
                "            \"href\": \"/companies/dee35d3c-d866-420d-a7ec-628ab5a302b8/data/accounts?orderBy=id&pageSize=3&page=3\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        String mockAccontsPayload2 = "{\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"id\": \"151fa7ea-86a1-45f2-adb1-049266fc8e7c\",\n" +
                "            \"nominalCode\": \"947\",\n" +
                "            \"name\": \"Student Loan Deductions Payable\",\n" +
                "            \"description\": \"Payroll student loan deductions payable account.\",\n" +
                "            \"fullyQualifiedCategory\": \"Liability.Current\",\n" +
                "            \"fullyQualifiedName\": \"Liability.Current.Student Loan Deductions Payable\",\n" +
                "            \"currency\": \"GBP\",\n" +
                "            \"currentBalance\": 0.00,\n" +
                "            \"type\": \"Liability\",\n" +
                "            \"status\": \"Active\",\n" +
                "            \"isBankAccount\": false,\n" +
                "            \"modifiedDate\": \"2023-08-11T14:21:30Z\",\n" +
                "            \"sourceModifiedDate\": \"2022-08-11T02:21:35\",\n" +
                "            \"validDatatypeLinks\": [],\n" +
                "            \"metadata\": {\n" +
                "                \"isDeleted\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"156376df-ad30-4221-8752-b99aedab3d57\",\n" +
                "            \"nominalCode\": \"810\",\n" +
                "            \"name\": \"Income in Advance\",\n" +
                "            \"description\": \"Any income the business has received but have not provided the goods or services for.\",\n" +
                "            \"fullyQualifiedCategory\": \"Liability.Current\",\n" +
                "            \"fullyQualifiedName\": \"Liability.Current.Income in Advance\",\n" +
                "            \"currency\": \"GBP\",\n" +
                "            \"currentBalance\": 0.00,\n" +
                "            \"type\": \"Liability\",\n" +
                "            \"status\": \"Active\",\n" +
                "            \"isBankAccount\": false,\n" +
                "            \"modifiedDate\": \"2023-08-11T14:21:30Z\",\n" +
                "            \"sourceModifiedDate\": \"2023-01-21T12:26:35\",\n" +
                "            \"validDatatypeLinks\": [],\n" +
                "            \"metadata\": {\n" +
                "                \"isDeleted\": false\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"1734ff00-2a17-45b4-8db6-2dc2e832c460\",\n" +
                "            \"nominalCode\": \"425\",\n" +
                "            \"name\": \"Postage, Freight & Courier\",\n" +
                "            \"description\": \"Expenses incurred on entertainment by the business that for income tax purposes are not fully deductible.\",\n" +
                "            \"fullyQualifiedCategory\": \"Expense.Operating\",\n" +
                "            \"fullyQualifiedName\": \"Expense.Operating.Postage, Freight & Courier\",\n" +
                "            \"currency\": \"GBP\",\n" +
                "            \"currentBalance\": 0.00,\n" +
                "            \"type\": \"Expense\",\n" +
                "            \"status\": \"Active\",\n" +
                "            \"isBankAccount\": false,\n" +
                "            \"modifiedDate\": \"2023-08-11T14:21:30Z\",\n" +
                "            \"sourceModifiedDate\": \"2023-04-04T18:14:35\",\n" +
                "            \"validDatatypeLinks\": [],\n" +
                "            \"metadata\": {\n" +
                "                \"isDeleted\": false\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageNumber\": 3,\n" +
                "    \"pageSize\": 3,\n" +
                "    \"totalResults\": 98,\n" +
                "    \"_links\": {\n" +
                "        \"current\": {\n" +
                "            \"href\": \"/companies/dee35d3c-d866-420d-a7ec-628ab5a302b8/data/accounts?orderBy=id&pageSize=3&page=3\"\n" +
                "        },\n" +
                "        \"self\": {\n" +
                "            \"href\": \"/companies/dee35d3c-d866-420d-a7ec-628ab5a302b8/data/accounts\"\n" +
                "        },\n" +
                "        \"previous\": {\n" +
                "            \"href\": \"/companies/dee35d3c-d866-420d-a7ec-628ab5a302b8/data/accounts?orderBy=id&pageSize=3&page=2\"\n" +
                "        },\n" +
                "        \"next\": {\n" +
                "            \"href\": \"/companies/dee35d3c-d866-420d-a7ec-628ab5a302b8/data/accounts?orderBy=id&pageSize=3&page=4\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        createAccounts(mockAccontsPayload1);
        createAccounts(mockAccontsPayload2);

    }

    private static void createAccounts(String jsonData) {
        var recordMetaData = ProducerUtil.publishMessageSync(ACCOUNTS_RAW, null, jsonData);
        log.info("Published the accountMessage message : {} ", recordMetaData);
    }
}
