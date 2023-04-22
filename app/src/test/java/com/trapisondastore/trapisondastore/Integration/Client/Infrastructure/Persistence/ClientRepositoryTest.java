package com.trapisondastore.trapisondastore.Integration.Client.Infrastructure.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
import com.trapisondastore.trapisondastore.Factory.Client.ClientBuilder;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
final class ClientRepositoryTest {

    @Autowired
    private ClientRepository repository;

    @Test
    void client_and_domain_event_are_stored_when_save_is_invoked() throws
        InvalidClientIdException,
        InvalidClientEmailException,
        InvalidClientPasswordException
    {
        Client client = ClientBuilder.defaultClient();

        Client signUpClient = Client.signUp(
            client.id().value().toString(),
            client.email().value(),
            client.password().value()
        );

        repository.save(signUpClient);

        assertEquals(Optional.of(client), repository.findById(signUpClient.id()));
    }
}
