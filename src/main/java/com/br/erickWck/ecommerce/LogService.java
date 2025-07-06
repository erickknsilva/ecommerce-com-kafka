package com.br.erickWck.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) {
        var logservice = new LogService();
        var kafkaService = new KafkaService(LogService.class.getSimpleName(),Pattern.compile("ECOMMERCE.*") , logservice::parse);
        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Recebendo logs: "+ record.key());
        System.out.println("LOG");
        System.out.println("Topic: " + record.topic());
        System.out.println("Key: " + record.key() + ", value: " + record.value());
        System.out.println(record.offset());
        System.out.println(record.partition());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
