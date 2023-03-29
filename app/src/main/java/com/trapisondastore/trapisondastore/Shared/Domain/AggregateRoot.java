package com.trapisondastore.trapisondastore.Shared.Domain;

import java.util.LinkedList;
import java.util.List;

public abstract class AggregateRoot {
    private List<DomainEvent> events;

    public AggregateRoot() {
        events = new LinkedList<DomainEvent>();
    }

    final protected void registerEvent(DomainEvent event) {
        events.add(event);
    }

    final public List<DomainEvent> pullEvents() {
        final List<DomainEvent> recordedDomainEvents = new LinkedList<DomainEvent>(events);
        
        events.clear();

        return recordedDomainEvents;
    }
}
