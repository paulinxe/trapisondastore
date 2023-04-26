package com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.Queue.Client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.DeliverCallback;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.Queue.RabbitQueue;

//@Scheduled(fixedDelay = 2000)
public class SendEmailOnClientSignedUpQueue extends RabbitQueue {

    public SendEmailOnClientSignedUpQueue() throws IOException, TimeoutException {
        super();
    }

    @Override
    public void pull() throws IOException {
        DeliverCallback callback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            // @TODO: conver to some kind of object and invoke use case
        };
        
        channel.basicConsume(
            "client.client.send_email_on_client_signed_up",
            true,
            callback,
            consumerTag -> { }
        );
    }
    
}
