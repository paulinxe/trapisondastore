package com.trapisondastore.trapisondastore.Integration.Client.Application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Client.Application.Exception.UnableToSignUpException;
import com.trapisondastore.trapisondastore.Client.Application.UseCase.SignUpUseCase;
import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.PasswordEncryptor;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
import com.trapisondastore.trapisondastore.Factory.Client.ClientBuilder;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEventPublisher;
import com.trapisondastore.trapisondastore.Shared.Domain.Exception.UnableToPublishDomainEvents;

@SpringBootTest
@ActiveProfiles("test")
public class SignUpUseCaseTest {
    
    @Autowired
    private ClientRepository repository;
    
    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @Test
    void client_is_not_saved_when_domain_event_publisher_fails() throws
        InvalidClientEmailException,
        InvalidClientIdException,
        InvalidClientPasswordException
    {
        SignUpUseCase useCase = new SignUpUseCase(
            repository,
            passwordEncryptor,
            new FakeDomainEventPublisher(),
            transactionManager
        );
        Client client = ClientBuilder.defaultClient();
        SignUpCommand command = new SignUpCommand(
            client.id().value().toString(),
            client.email().value(),
            client.password().value()
        );

        assertThrows(UnableToSignUpException.class, () -> {
            useCase.execute(command);
        });

        var notFoundClient = repository.findById(client.id());

        assertTrue(notFoundClient.isEmpty());
    }

    class FakeDomainEventPublisher implements DomainEventPublisher {
        @Override
        public void publish(List<DomainEvent> events) throws UnableToPublishDomainEvents {
            throw new UnableToPublishDomainEvents();
        }
    }
}
