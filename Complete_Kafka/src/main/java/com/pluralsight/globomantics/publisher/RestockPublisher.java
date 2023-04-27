package com.pluralsight.globomantics.publisher;

import java.net.InetAddress;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import jakarta.ejb.Singleton;

@Singleton
public class RestockPublisher {

	public void sendRestockOrder(String restockOrder) {
		
		Properties config = new Properties();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
		config.put(ProducerConfig.ACKS_CONFIG, "all");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		
		KafkaProducer<String,String> restockProducer = new KafkaProducer<>(config);
		final ProducerRecord<String, String> record = new ProducerRecord<>("restock", null, restockOrder);
		Future<RecordMetadata> future = restockProducer.send(record);
	}

}
