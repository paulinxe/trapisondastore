package com.trapisondastore.trapisondastore.Unit.Shared.Domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;

public class DomainEventTest {
    @Test
    void event_has_an_uuid() {
        var fake = new FakeDomainEvent();

        assertInstanceOf(UUID.class, fake.getId());
    }

    @Test
    void event_is_not_processed_by_default() {
        var fake = new FakeDomainEvent();

        assertTrue(fake.getProcessedAt().isEmpty());
    }

    @Test
    void event_tries_is_zero_by_default() {
        var fake = new FakeDomainEvent();

        assertEquals(0, fake.getTries());
    }

    @Test
    void event_has_current_time_as_created_at() {
        Instant now = Instant.now();
        var fake = new FakeDomainEvent();

        Duration duration = Duration.between(fake.getCreatedAt(), now);
        long seconds = duration.getSeconds();

        assertTrue(seconds <= 1);
    }

    @Test
    void event_payload_is_formed_correctly() {
        var fake = new FakeDomainEvent();

        assertTrue(fake.getPayload().contains("data"));

        assertTrue(fake.getPayload().contains("id"));
        assertTrue(fake.getPayload().contains("type"));
        assertTrue(fake.getPayload().contains("attributes"));
        assertTrue(fake.getPayload().contains("occurred_on"));
    }

    private class FakeDomainEvent extends DomainEvent {
        public FakeDomainEvent() {
            super(new HashMap<String, Object>(), "");
        }
    }
}
