package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence;

import java.util.List;
import org.springframework.stereotype.Service;
import com.trapisondastore.trapisondastore.Shared.Domain.AggregateRoot;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;
import com.trapisondastore.trapisondastore.Shared.Domain.Repository;

@Service
public abstract class MySQLRepository implements Repository {
    @Override
    public void registerDomainEvents(AggregateRoot aggregateRoot) {
        List<DomainEvent> events = aggregateRoot.pullEvents();

        for (var event : events) {
            save(event);
        }
    }
}
