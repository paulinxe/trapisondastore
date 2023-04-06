package com.trapisondastore.trapisondastore.Unit.Client.Application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Client.Application.Command.UseCase.SignUpUseCase;
import com.trapisondastore.trapisondastore.Client.Application.Exception.UnableToSignUpException;
import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
import com.trapisondastore.trapisondastore.Factory.Client.ClientBuilder;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception.UnableToBuildAggregateRootException;

@ExtendWith(MockitoExtension.class)
final class SignUpUseCaseTest {

    @InjectMocks
    private SignUpUseCase useCase;

    @Mock
    private ClientRepository repository;

    public void setup() {
        repository = Mockito.mock(ClientRepository.class);
    }

    @Test
    void exception_is_thrown_when_client_already_exists()
            throws UnableToBuildAggregateRootException, InvalidClientEmailException, InvalidClientIdException,
            InvalidClientPasswordException {
        final Client existingClient = ClientBuilder.defaultClient();
        Mockito.when(repository.findByEmail(any())).thenReturn(Optional.of(existingClient));

        var exception = assertThrows(UnableToSignUpException.class, () -> {
            executeUseCase(existingClient.id().value().toString(), existingClient.email().value(),
                    existingClient.password().value());
        });

        assertEquals(UnableToSignUpException.CLIENT_EXISTS, exception.ERROR_CODE);
    }

    @Test
    void exception_is_thrown_when_email_is_invalid() {

    }

    @Test
    void exception_is_thrown_when_id_is_invalid() {

    }

    @Test
    void exception_is_thrown_when_password_is_invalid() {

    }

    private void executeUseCase(String id, String email, String password) throws UnableToSignUpException {
        SignUpCommand command = new SignUpCommand(id, email, password);
        useCase.execute(command);
    }
}
