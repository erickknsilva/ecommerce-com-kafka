package com.br.erickWck.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class FraudeDetectorService {

    public static void main(String[] args) {

        var fraudeDetector = new FraudeDetectorService();
        try (var kafkaService = new KafkaService<Order>(FraudeDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER", fraudeDetector::parse, Order.class,
                new HashMap<>())) {
            kafkaService.run();
        }
    }


    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
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


        Order order = record.value();
        if (isFraud(order)) {
            System.out.println("Order approved:  " + order);
            orderDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getEmail(), order);
        } else {
            System.out.println("Order is fraud: " + order);
            orderDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getEmail(), order);
        }


    }

    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();

    private static boolean isFraud(Order order) {
        return order.getAmount().compareTo(new BigDecimal("4500")) >= 0;
    }

}
