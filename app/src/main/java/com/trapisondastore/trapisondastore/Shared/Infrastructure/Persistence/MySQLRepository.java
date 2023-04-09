package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.trapisondastore.trapisondastore.Shared.Domain.AggregateRoot;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;
import com.trapisondastore.trapisondastore.Shared.Domain.Repository;

public abstract class MySQLRepository implements Repository {

    @Autowired
    private JPAMySQLEventStoreRepository repository;

    @Override
    public void registerDomainEvents(AggregateRoot aggregateRoot) {
        List<DomainEvent> events = aggregateRoot.pullEvents();

        for (var event : events) {
            repository.save(event);
        }
    }

}
