package com.pluralsight.globomantics.listener;

import java.io.StringReader;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.pluralsight.globomantics.services.Service;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class ProductSupplier {

    @Inject
    private Service productService;

    private final KafkaConsumer<String, String> consumer;
 

    public ProductSupplier() {

        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "product-supplier");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(config);
    
    }

    public void listen() {
        try {
            consumer.subscribe(Arrays.asList("restock"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                records.forEach(record -> process(record));
            }
        } finally {
            consumer.close();
        }
    }


    public void process(ConsumerRecord<String, String> record) {
        try {
            JsonObject restockRequest = Json.createReader(new StringReader(record.value()))
                    .readObject();

            String productType = restockRequest.getString("productType");

            System.out.println(String.format("Restocking %s ...", productType));

            productService.update(productType);

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
