package com.trapisondastore.trapisondastore.Client.Application.UseCase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception.UnableToBuildAggregateRootException;

@Service
public class SignUpUseCase {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

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
            ClientPassword password = ClientPassword.fromPlain(command.password);

            Client client = Client.signUp(command.id, command.email, passwordEncryptor.encrypt(password.value()));

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
