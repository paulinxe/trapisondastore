package com.trapisondastore.trapisondastore.Client.Infrastructure.Persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.MySQLRepository;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception.UnableToBuildAggregateRootException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

@Service
public class JPAClientRepository extends MySQLRepository implements ClientRepository {
    
    @Autowired
    private JPAClientRepositoryDefinition jpaRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<Client> findByEmail(ClientEmail email) throws UnableToBuildAggregateRootException {
        var client = jpaRepository.findFirstByEmail(email.value());
        if (client == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(
                Client.fromPersistence(
                    client.getId().toString(),
                    client.getName(),
                    client.getEmail(),
                    client.getAddress()
                )
            );
        } catch (InvalidClientIdException | InvalidClientEmailException e) {
            throw new UnableToBuildAggregateRootException(e);
        }
    }

    @Transactional
    @Override
    public void save(Client client) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            JPAClient jpaClient = new JPAClient(client);
            em.persist(jpaClient);
            registerDomainEvents(client);
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
