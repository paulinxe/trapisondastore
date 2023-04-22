package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;

@Service
@Transactional("trapisondastore-transaction_manager")
public class MySQLEventStoreRepository extends HibernateRepository<DomainEvent> {
    public MySQLEventStoreRepository(SessionFactory sessionFactory) {
        super(sessionFactory, DomainEvent.class);
    }

    public List<DomainEvent> getUnprocessed() {
        String statement = "SELECT * FROM event_store WHERE processed_at IS NULL";

        return sessionFactory.getCurrentSession()
            .createNativeQuery(statement, DomainEvent.class)
            .getResultList()
            .stream()
            .toList();
    }

    public void save(DomainEvent event) {
        persist(event);
    }
}
