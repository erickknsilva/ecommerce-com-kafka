package com.br.erickWck.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class FraudeDetectorService {

    public static void main(String[] args) {

        var fraudeDetector = new FraudeDetectorService();
        try (var kafkaService = new KafkaService<Order>(FraudeDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER", fraudeDetector::parse, Order.class,
                new HashMap<>())) {
            kafkaService.run();
        }
    }


    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Processando new Orderd, checking for fraud");
        System.out.println("Key: " + record.key() + ", value: " + record.value());
        System.out.println(record.offset());
        System.out.println(record.partition());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ordem processed");
    }

}
