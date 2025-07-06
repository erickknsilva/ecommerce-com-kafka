package com.br.erickWck.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();
        try (var service = new KafkaService(FraudeDetectorService.class.getSimpleName(), "ECOMMERCE_SEND_EMAIL", emailService::parse)) {

            service.run();
        }
    }


    private void parse(ConsumerRecord<String, String> record) {
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
