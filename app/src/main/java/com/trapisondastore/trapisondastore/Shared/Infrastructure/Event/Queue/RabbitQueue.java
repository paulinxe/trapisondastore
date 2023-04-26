package com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.Queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.RabbitMQConnection;

public abstract class RabbitQueue {

    private Channel channel;
    protected Logger logger;

    public RabbitQueue(RabbitMQConnection connection) throws IOException, TimeoutException {
        channel = connection.connect();
        logger = LoggerFactory.getLogger(RabbitQueue.class);
    }

    public abstract void process() throws IOException;

    final protected void pull(String queue, DeliverCallback callback) throws IOException {
        channel.basicConsume(
            queue,
            true,
            callback,
            consumerTag -> { }
        );
    };

    protected void logMessageReceived(String className, String queueName) {
        logger.info(
            String.format(
                "[%s] Received message from %s",
                className,
                queueName
            )
        );
    }
}
