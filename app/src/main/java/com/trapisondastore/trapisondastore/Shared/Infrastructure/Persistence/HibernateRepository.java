package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence;

import java.util.Optional;
import org.hibernate.SessionFactory;

public abstract class HibernateRepository <T> {
    protected final SessionFactory sessionFactory;
    protected final Class<T> aggregateClass;

    public HibernateRepository(SessionFactory sessionFactory, Class<T> aggregateClass) {
        this.sessionFactory = sessionFactory;
        this.aggregateClass = aggregateClass;
    }

    protected void persist(T entity) {
        sessionFactory.getCurrentSession().persist(entity);
        sessionFactory.getCurrentSession().flush();
    }
    
    protected <V> Optional<T> byId(V id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().byId(aggregateClass).load(id));
    }
}
