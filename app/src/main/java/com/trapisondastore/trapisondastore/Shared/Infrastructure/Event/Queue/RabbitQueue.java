package com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.Queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import com.rabbitmq.client.Channel;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.RabbitMQConnection;

public abstract class RabbitQueue {

    @Autowired
    private RabbitMQConnection connection;
    protected Channel channel;

    public RabbitQueue() throws IOException, TimeoutException {
        channel = connection.connect();
    }

    public abstract void pull() throws IOException;
}
