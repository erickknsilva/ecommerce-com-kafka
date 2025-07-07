package com.br.erickWck.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher<T> implements Closeable {


    private final KafkaProducer<String, T> producer;

    public KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());


    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.1.1:9092");
        //serializa a chave do tipo string para bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //serializa o valor do tipo string para bytes
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GssonSerializer.class.getName());
        return properties;
    }

    public void send(String topic, String key, T t) throws ExecutionException, InterruptedException {

        var record = new ProducerRecord<>(topic, key, t);

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Sucesso enviando nesse topico data.topic()" + ":" + data.partition() + "/ offseat " + data.offset());
        };
        producer.send(record, callback).get();
    }


    @Override
    public void close() {
        producer.close();
    }
}
