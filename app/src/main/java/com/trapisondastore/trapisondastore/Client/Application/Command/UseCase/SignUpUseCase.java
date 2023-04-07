package com.trapisondastore.trapisondastore.Client.Application.Command.UseCase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Client.Application.Exception.UnableToSignUpException;
import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception.UnableToBuildAggregateRootException;

public class SignUpUseCase {

    @Autowired
    private ClientRepository repository;

    public void execute(SignUpCommand command) throws UnableToSignUpException {
        try {
            Optional<Client> existingClient = repository.findByEmail(
                    new ClientEmail(command.email));

            if (existingClient.isPresent()) {
                throw UnableToSignUpException.clientAlreadyExists();
            }
        } catch (UnableToBuildAggregateRootException e) {
            throw UnableToSignUpException.clientAlreadyExists();
        } catch (InvalidClientEmailException e) {
            throw UnableToSignUpException.emailNotValid();            
        }

        try {
            Client client = Client.signUp(command.id, command.email, command.password);
            repository.save(client);
        } catch (InvalidClientEmailException e) {
            throw UnableToSignUpException.emailNotValid();
        } catch (InvalidClientPasswordException e) {
            throw UnableToSignUpException.passwordNotValid();
        } catch (InvalidClientIdException e) {
            throw UnableToSignUpException.idNotValid();
        }
    }
}
