package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence;

import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import com.trapisondastore.trapisondastore.Shared.Domain.AggregateRoot;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;

public abstract class HibernateRepository <T> {
    protected final SessionFactory sessionFactory;
    protected final Class<T> aggregateClass;

    public HibernateRepository(SessionFactory sessionFactory, Class<T> aggregateClass) {
        this.sessionFactory = sessionFactory;
        this.aggregateClass = aggregateClass;
    }

    protected void persist(T aggregateRoot) {
        sessionFactory.getCurrentSession().persist(aggregateRoot);
        
        /*List<DomainEvent> events = aggregateRoot.pullEvents();
        for (var event : events) {
            sessionFactory.getCurrentSession().persist(event);
        }*/

        sessionFactory.getCurrentSession().flush();
    }
    
    protected <V> Optional<T> byId(V id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().byId(aggregateClass).load(id));
    }
}
