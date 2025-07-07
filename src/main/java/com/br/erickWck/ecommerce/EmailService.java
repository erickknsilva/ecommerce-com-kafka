package com.br.erickWck.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();
        try (var service = new KafkaService<>(EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL", emailService::parse, Email.class,
                new HashMap<>())) {

            service.run();
        }
    }


    private void parse(ConsumerRecord<String, Email> record) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Enviando email");
        System.out.println("Key: " + record.key() + ", value: " + record.value());
        System.out.println(record.offset());
        System.out.println(record.partition());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Email enviado");
    }

}
