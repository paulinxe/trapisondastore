package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;
import com.trapisondastore.trapisondastore.Shared.Domain.EventStoreRepository;
import jakarta.persistence.EntityManager;

@Service
public class JPAMySQLEventStoreRepository implements EventStoreRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(DomainEvent event) {
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = "";

        try {
            payload = objectMapper.writeValueAsString(event.getPayload());

            JPAEventStore jpaEvent = new JPAEventStore(
                event.getId(),
                payload,
                event.isProcessed(),
                event.getTries(),
                event.getCreatedAt(),
                event.getProcessedAt().isPresent() ? event.getProcessedAt().get() : null
            );

            entityManager.persist(jpaEvent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
