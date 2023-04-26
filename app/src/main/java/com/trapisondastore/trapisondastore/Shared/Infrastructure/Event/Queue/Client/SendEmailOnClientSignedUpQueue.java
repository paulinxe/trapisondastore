package com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.Queue.Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import com.trapisondastore.trapisondastore.Client.Application.Command.SendSignUpEmailCommand;
import com.trapisondastore.trapisondastore.Client.Application.UseCase.SendSignUpEmail;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.RabbitMQConnection;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Event.Queue.RabbitQueue;

@Service
public class SendEmailOnClientSignedUpQueue extends RabbitQueue {

    private final static String QUEUE_NAME = "client.client.send_email_on_client_signed_up";

    @Autowired
    private SendSignUpEmail useCase;

    @Autowired
    public SendEmailOnClientSignedUpQueue(RabbitMQConnection connection) throws IOException, TimeoutException {
        super(connection);
    }

    @Override
    @Scheduled(fixedDelay = 1000)
    public void process() throws IOException {
        DeliverCallback callback = (consumerTag, delivery) -> {
            logMessageReceived(SendEmailOnClientSignedUpQueue.class.getName(), QUEUE_NAME);

            String message = new String(delivery.getBody(), "UTF-8");
            var json = new ObjectMapper().readValue(message, HashMap.class);

            useCase.execute(new SendSignUpEmailCommand(json.get("email").toString()));
        };
        
        pull(QUEUE_NAME, callback);
    }
    
}
