package com.trapisondastore.trapisondastore.Shared.Domain;

import java.util.List;
import com.trapisondastore.trapisondastore.Shared.Domain.Exception.UnableToPublishDomainEvents;

public interface DomainEventPublisher {
    public void publish(List<DomainEvent> events) throws UnableToPublishDomainEvents;
}
