package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence;

import java.time.Instant;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_store")
public class JPAEventStore {
    
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String payload;
    private boolean processed;
    private int tries;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "processed_at")
    private Instant processedAt;

    public JPAEventStore() {

    }

    public JPAEventStore(
        UUID id,
        String payload,
        boolean processed,
        int tries,
        Instant createdAt,
        Instant processedAt
    ) {
        this.id = id;
        this.payload = payload;
        this.processed = processed;
        this.tries = tries;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getPayload() {
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

    public Instant getProcessedAt() {
        return processedAt;
    }
}
