package com.trapisondastore.trapisondastore.Shared.Infrastructure.Event;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEventPublisher;
import com.trapisondastore.trapisondastore.Shared.Domain.Exception.UnableToPublishDomainEvents;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Service
public class RabbitMQDomainEventPublisher implements DomainEventPublisher {

    @Autowired
    private RabbitMQConnection connection;

    @Override
    public void publish(List<DomainEvent> events) throws UnableToPublishDomainEvents {
        if (events.isEmpty()) {
            return;
        }

        Channel channel;
        try {
            channel = connection.connect();
        } catch (Exception e) {
            throw new UnableToPublishDomainEvents(e.getMessage(), e);
        }

        try {
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
