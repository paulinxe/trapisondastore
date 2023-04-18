package com.trapisondastore.trapisondastore.Shared.Domain;

public interface Repository {
    public void registerDomainEvents(AggregateRoot aggregateRoot);
    public void save(DomainEvent event);
}
