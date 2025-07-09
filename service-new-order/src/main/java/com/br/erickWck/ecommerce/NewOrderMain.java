package com.br.erickWck.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var kafkaDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<Email>()) {

                var emailRandom = Math.random() + "@emafsdil.com";
                for (int i = 0; i < 10; i++) {

                    String orderId = UUID.randomUUID().toString();
                    var ammount = Math.random() * 5000 + 1;
                    var order = new Order(orderId, new BigDecimal(ammount), emailRandom);

                    String email = "erickk.nunes100@gmail.com";
                    String body = "Obrigado por realizar sua compra.";
                    var emailteste = new Email(email, body);
                    kafkaDispatcher.send("ECOMMERCE_NEW_ORDER", orderId, order);
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", emailRandom, emailteste);

                }
            }
        }
    }
}
