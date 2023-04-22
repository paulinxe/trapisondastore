package com.trapisondastore.trapisondastore.Shared.Infrastructure.Jobs;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.MySQLEventStoreRepository;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

@Service
public class PublishDomainEvents {

    @Value("${rabbit.host}")
    private String HOST;

    @Value("${rabbit.username}")
    private String USERNAME;

    @Value("${rabbit.password}")
    private String PASSWORD;

    @Value("${rabbit.vhost}")
    private String VHOST;

    @Autowired
    private MySQLEventStoreRepository repository;

    @Scheduled(fixedDelay = 10000)
    public void run() throws Exception {
        var events = repository.getUnprocessed();

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

                channel.basicPublish(
                    data.get("type").toString().split("\\.")[1],
                    data.get("type").toString(),
                    false,
                    false,
                    null,
                    events.get(0).getPayload().getBytes()
                );
            }
        }
    }
}
