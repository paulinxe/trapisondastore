package com.trapisondastore.trapisondastore.Shared.Domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class DomainEvent {
    private UUID id;
    private String payload;
    private int tries;
    private Timestamp createdAt;
    private Timestamp processedAt;

    public DomainEvent() {

    }

    public DomainEvent(
        HashMap<String, Object> eventAttributes,
        String domainEventFQN
    ) {
        this.id = UUID.randomUUID();
        this.tries = 0;
        this.createdAt = Timestamp.from(Instant.now());
        this.processedAt = null;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        var data = new HashMap<String, Object>(){{
            put("id", id.toString());
            put("type", domainEventFQN);
            put("occurred_on", formatter.format(createdAt.toInstant())); // @TODO: dafuq
            put("attributes", eventAttributes);
        }};
        try { // @TODO: we shouldn't use infra here
            this.payload = new ObjectMapper().writeValueAsString(
                new HashMap<String, Object>(){{ put("data", data); }}
            );
        } catch (JsonProcessingException e) {
            this.payload = null;
            e.printStackTrace();
        }
    }

    public UUID getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public int getTries() {
        return tries;
    }

    public Instant getCreatedAt() {
        return createdAt.toInstant();
    }

    public Optional<Instant> getProcessedAt() {
        return Optional.ofNullable(processedAt != null ? processedAt.toInstant() : null);
    }
}
