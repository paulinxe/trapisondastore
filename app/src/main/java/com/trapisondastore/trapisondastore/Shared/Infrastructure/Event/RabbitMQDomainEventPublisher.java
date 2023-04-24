package com.trapisondastore.trapisondastore.Shared.Infrastructure.Event;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEventPublisher;
import com.trapisondastore.trapisondastore.Shared.Domain.Exception.UnableToPublishDomainEvents;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Service
public class RabbitMQDomainEventPublisher implements DomainEventPublisher {

    @Value("${rabbit.host}")
    private String HOST;

    @Value("${rabbit.username}")
    private String USERNAME;

    @Value("${rabbit.password}")
    private String PASSWORD;

    @Value("${rabbit.vhost}")
    private String VHOST;

    @Override
    public void publish(List<DomainEvent> events) throws UnableToPublishDomainEvents {
        if (events.isEmpty()) {
            return;
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VHOST);

        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel())
        {
            for (var event : events) {
                var payload = event.getPayload();

                HashMap<String, Object> json = new ObjectMapper().readValue(payload, HashMap.class);

                var data = ((HashMap<String, Object>) json.get("data"));

                channel.basicPublish(data.get("type").toString().split("\\.")[1],
                        data.get("type").toString(), false, false, null,
                        events.get(0).getPayload().getBytes());
            }
        } catch (Exception e) {
            throw new UnableToPublishDomainEvents(e.getMessage(), e);
        }
    }
}
