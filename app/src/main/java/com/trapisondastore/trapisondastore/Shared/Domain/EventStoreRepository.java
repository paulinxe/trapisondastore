package com.trapisondastore.trapisondastore.Shared.Domain;

public interface EventStoreRepository {
    public void save(DomainEvent event);
}
