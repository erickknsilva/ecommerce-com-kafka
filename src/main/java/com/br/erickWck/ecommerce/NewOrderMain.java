package com.br.erickWck.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var kafkaDispatcher = new KafkaDispatcher()) {


            for (int i = 0; i < 10; i++) {

                String key = UUID.randomUUID().toString();
                String value = "21323, 2342,3234";
                String email = "erickk.nunes100@gmail.com";

                kafkaDispatcher.send("ECOMMERCE_NEW_ORDER", key, value);
                kafkaDispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);

            }

        }
    }


}
