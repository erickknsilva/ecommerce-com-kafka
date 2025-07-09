package com.br.erickWck;

import com.br.erickWck.ecommerce.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class CreateUserService {

    public static void main(String[] args) {

        var createService = new CreateUserService();

        try (var kafkaService = new KafkaService<Order>(CreateUserService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                createService::parse,
                Order.class,
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
            orderDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getUserdId(), order);
        } else {
            System.out.println("Order is fraud: " + order);
            orderDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getUserdId(), order);
        }


    }
}
