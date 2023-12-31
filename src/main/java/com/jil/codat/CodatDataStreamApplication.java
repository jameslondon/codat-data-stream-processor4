package com.jil.codat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableKafkaStreams
public class CodatDataStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodatDataStreamApplication.class, args);
	}

}
