package com.trapisondastore.trapisondastore.Unit.Client.Application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEventPublisher;
import com.trapisondastore.trapisondastore.Shared.Domain.Exception.UnableToPublishDomainEvents;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception.UnableToBuildAggregateRootException;

@ExtendWith(MockitoExtension.class)
final class SignUpUseCaseTest {

    @InjectMocks
    private SignUpUseCase useCase;

    @Mock
    private ClientRepository repository;

    @Mock
    private PasswordEncryptor passwordEncryptor;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private DomainEventPublisher publisher;

    public void setup() {
        repository = Mockito.mock(ClientRepository.class);
        passwordEncryptor = Mockito.mock(PasswordEncryptor.class);
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

    @ParameterizedTest
    @MethodSource("validClientData")
    void exception_is_thrown_when_email_is_invalid(String id, String email, String password) {
        final String invalidEmail = "abc";

        var exception = assertThrows(UnableToSignUpException.class, () -> {
            executeUseCase(id, invalidEmail, password);
        });

        assertEquals(UnableToSignUpException.EMAIL_NOT_VALID, exception.ERROR_CODE);
    }

    @ParameterizedTest
    @MethodSource("validClientData")
    void exception_is_thrown_when_id_is_invalid(String id, String email, String password) {
        final String invalidId = "68f5-4691-9e9e-0af0e90ebf7a";

        var exception = assertThrows(UnableToSignUpException.class, () -> {
            executeUseCase(invalidId, email, password);
        });

        assertEquals(UnableToSignUpException.ID_NOT_VALID, exception.ERROR_CODE);
    }

    @ParameterizedTest
    @MethodSource("validClientData")
    void exception_is_thrown_when_password_is_invalid(String id, String email, String password) {
        final String invalidPassword = "123";

        var exception = assertThrows(UnableToSignUpException.class, () -> {
            executeUseCase(id, email, invalidPassword);
        });

        assertEquals(UnableToSignUpException.PASSWORD_NOT_VALID, exception.ERROR_CODE);
    }

    @ParameterizedTest
    @MethodSource("validClientData")
    void repository_is_called_when_client_data_is_valid(String id, String email, String password)
            throws UnableToSignUpException, UnableToPublishDomainEvents {
        Mockito.when(passwordEncryptor.encrypt(any(String.class))).thenReturn("asdsada");

        executeUseCase(id, email, password);

        verify(repository, times(1)).save(any(Client.class));
        verify(passwordEncryptor, times(1)).encrypt(any(String.class));
    }

    private void executeUseCase(String id, String email, String password) throws UnableToSignUpException, UnableToPublishDomainEvents {
        SignUpCommand command = new SignUpCommand(id, email, password);
        useCase.execute(command);
    }

    public static Object[][] validClientData() {
        return new Object[][] {
            { "f1c5b5e5-68f5-4691-9e9e-0af0e90ebf7a", "abc@test.com", "abcdAbcd1" }
        };
    }
}
