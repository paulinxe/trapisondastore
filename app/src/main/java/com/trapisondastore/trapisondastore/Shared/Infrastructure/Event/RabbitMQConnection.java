package com.trapisondastore.trapisondastore.Shared.Infrastructure.Event;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Service
public class RabbitMQConnection {
    @Value("${rabbit.host}")
    private String HOST;

    @Value("${rabbit.username}")
    private String USERNAME;

    @Value("${rabbit.password}")
    private String PASSWORD;

    @Value("${rabbit.vhost}")
    private String VHOST;

    public Channel connect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VHOST);

        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
