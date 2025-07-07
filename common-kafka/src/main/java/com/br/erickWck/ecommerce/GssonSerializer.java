package com.br.erickWck.ecommerce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class GssonSerializer<T> implements Serializer<T> {

   private final Gson gson =  new GsonBuilder().create();

    @Override
    public byte[] serialize(String topic, T object) {
      return   gson.toJson(object).getBytes();
    }


}
