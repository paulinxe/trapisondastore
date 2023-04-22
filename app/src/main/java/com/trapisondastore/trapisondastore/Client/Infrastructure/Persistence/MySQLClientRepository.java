package com.trapisondastore.trapisondastore.Client.Infrastructure.Persistence;

import java.util.Optional;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientId;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.HibernateRepository;

@Service
@Transactional("trapisondastore-transaction_manager")
public class MySQLClientRepository extends HibernateRepository<Client> implements ClientRepository {

    public MySQLClientRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Client.class);
    }

    @Override
    public Optional<Client> findByEmail(ClientEmail email) {
        String jpql = "SELECT * FROM clients WHERE email = :email";
        var client = sessionFactory.getCurrentSession()
                .createNativeQuery(jpql, Client.class)
                .setParameter("email", email.value())
                .getResultList()
                .stream()
                .findFirst();

        return client;
    }

    @Override
    public void save(Client client) {
        persist(client);
    }

    @Override
    public Optional<Client> findById(ClientId id) {
        return byId(id);
    }
}
