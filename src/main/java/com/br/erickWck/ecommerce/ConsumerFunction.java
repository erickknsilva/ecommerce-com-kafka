package com.br.erickWck.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;

public interface ConsumerFunction {

    void consume(ConsumerRecord<String, String> record);
}
