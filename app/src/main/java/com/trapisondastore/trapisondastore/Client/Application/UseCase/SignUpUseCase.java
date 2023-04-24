package com.trapisondastore.trapisondastore.Client.Application.UseCase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Client.Application.Exception.UnableToSignUpException;
import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.PasswordEncryptor;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientPassword;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEventPublisher;
import com.trapisondastore.trapisondastore.Shared.Domain.Exception.UnableToPublishDomainEvents;

@Service
public class SignUpUseCase {
    private ClientRepository repository;
    private PasswordEncryptor passwordEncryptor;
    private DomainEventPublisher publisher;
    private PlatformTransactionManager transactionManager;

    @Autowired
    public SignUpUseCase(
        ClientRepository repository,
        PasswordEncryptor passwordEncryptor,
        DomainEventPublisher publisher,
        PlatformTransactionManager transactionManager)
    {
        this.repository = repository;
        this.passwordEncryptor = passwordEncryptor;
        this.publisher = publisher;
        this.transactionManager = transactionManager;
    }

    public void execute(SignUpCommand command) throws UnableToSignUpException {
        try {
            Optional<Client> existingClient = repository.findByEmail(
                    new ClientEmail(command.email));

            if (existingClient.isPresent()) {
                throw UnableToSignUpException.clientAlreadyExists();
            }
        } catch (InvalidClientEmailException e) {
            throw UnableToSignUpException.emailNotValid();
        }

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);

        try {
            ClientPassword password = ClientPassword.fromPlain(command.password);
            Client client = Client.signUp(command.id, command.email, passwordEncryptor.encrypt(password.value()));

            repository.save(client);
            publisher.publish(client.pullEvents());
            transactionManager.commit(status);
        } catch (InvalidClientEmailException e) {
            transactionManager.rollback(status);
            throw UnableToSignUpException.emailNotValid();
        } catch (InvalidClientPasswordException e) {
            transactionManager.rollback(status);
            throw UnableToSignUpException.passwordNotValid();
        } catch (InvalidClientIdException e) {
            transactionManager.rollback(status);
            throw UnableToSignUpException.idNotValid();
        } catch (UnableToPublishDomainEvents e) {
            transactionManager.rollback(status);
            throw UnableToSignUpException.eventPublisherFailed();
        }
    }
}
