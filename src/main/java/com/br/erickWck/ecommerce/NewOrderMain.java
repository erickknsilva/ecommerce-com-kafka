package com.br.erickWck.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var kafkaDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<Email>()) {

                for (int i = 0; i < 10; i++) {

                    String userId = UUID.randomUUID().toString();
                    String orderId = UUID.randomUUID().toString();
                    var ammount = Math.random() * 5000 + 1;

                    var order = new Order(userId, orderId, new BigDecimal(ammount));
                    String email = "erickk.nunes100@gmail.com";
                    String body = "Obrigado por realizar sua compra.";
                    var emailteste = new Email(email, body);
                    kafkaDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, emailteste);

                }
            }

        }
    }


}
