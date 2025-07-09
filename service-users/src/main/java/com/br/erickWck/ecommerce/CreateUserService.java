package com.br.erickWck.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class CreateUserService {

    private final Connection connection;

    CreateUserService() throws SQLException {
        String url = "jdbc:sqlite:users_database_db";
        String createTable = "create table if not exists Users (" +
                "uuid varchar(200) primary key," + "email varchar(200))";
        this.connection = DriverManager.getConnection(url);
        try {
            connection.createStatement().execute("create table Users (" +
                    "uuid varchar(200) primary key," +
                    "email varchar(200))");
        } catch(SQLException ex) {
            // be careful, the sql could be wrong, be reallllly careful
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {

        var createService = new CreateUserService();

        try (var kafkaService = new KafkaService<Order>(CreateUserService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                createService::parse,
                Order.class,
                new HashMap<>())) {
            kafkaService.run();
        }


    }

    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException, SQLException {
        System.out.println("-----------------------------------------------------");
        System.out.println("Key: " + record.key() + ", value: " + record.value());
        System.out.println(record.offset());
        System.out.println(record.partition());
        var order = record.value();
        if(isNewUser(order.getEmail())) {
            insertNewUser(order.getEmail());
        }
    }


    private void insertNewUser(String email) throws SQLException {
        System.out.println("Usuário uuid e " + email + " adicionado");
        var insert = connection.prepareStatement("insert into Users (uuid, email) " +
                "values (?,?)");
        insert.setString(1, UUID.randomUUID().toString());
        insert.setString(2, email);
        insert.execute();
    }

    private boolean isNewUser(String email) throws SQLException {
        var exists = connection.prepareStatement("select uuid from Users " +
                "where email = ? limit 1");
        exists.setString(1, email);
        var results = exists.executeQuery();
        return !results.next();
    }

}
