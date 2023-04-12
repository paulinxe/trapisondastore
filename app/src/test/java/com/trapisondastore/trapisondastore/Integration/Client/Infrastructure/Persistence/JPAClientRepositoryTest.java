package com.trapisondastore.trapisondastore.Integration.Client.Infrastructure.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
import com.trapisondastore.trapisondastore.Client.Infrastructure.Persistence.JPAClientRepository;
import com.trapisondastore.trapisondastore.Factory.Client.ClientBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class JPAClientRepositoryTest {

    @Autowired
    private JPAClientRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void client_and_domain_event_are_stored_when_save_is_invoked()
            throws InvalidClientEmailException, InvalidClientIdException,
            InvalidClientPasswordException {
        Client client = ClientBuilder.defaultClient();

        repository.save(client);

        Query query = entityManager.createNativeQuery("SELECT COUNT(1) FROM clients c WHERE c.id = UNHEX(REPLACE(:id, \"-\", \"\"))");
        query.setParameter("id", client.id().value().toString());

        assertEquals(1L, query.getSingleResult());
    }
}
