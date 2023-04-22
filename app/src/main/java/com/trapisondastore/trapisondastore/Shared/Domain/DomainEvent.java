package com.trapisondastore.trapisondastore.Shared.Domain;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public abstract class DomainEvent {
    private UUID id;
    private HashMap<String, Object> payload;
    private boolean processed;
    private int tries;
    private Instant createdAt;
    private Instant processedAt;

    public DomainEvent() {

    }

    public DomainEvent(
        HashMap<String, Object> eventAttributes,
        String domainEventFQN
    ) {
        this.id = UUID.randomUUID();
        this.processed = false;
        this.tries = 0;
        this.createdAt = Instant.now();
        this.processedAt = null;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        var data = new HashMap<String, Object>(){{
            put("id", id.toString());
            put("type", domainEventFQN);
            put("occurred_on", formatter.format(createdAt));
            put("attributes", eventAttributes);
        }};
        this.payload = new HashMap<String, Object>(){{ put("data", data); }};
    }

    public UUID getId() {
        return id;
    }

    public HashMap<String, Object> getPayload() {
        return payload;
    }

    public boolean isProcessed() {
        return processed;
    }

    public int getTries() {
        return tries;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Optional<Instant> getProcessedAt() {
        return Optional.ofNullable(processedAt);
    }
}
