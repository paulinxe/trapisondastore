package com.trapisondastore.trapisondastore.Unit.Client.Domain.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;

final class ClientEmailTest {
    
    @ParameterizedTest
    @MethodSource("invalidEmailsProvider")
    void exception_is_thrown_when_email_is_invalid(String invalidEmail) {
        assertThrows(InvalidClientEmailException.class, () -> {
            new ClientEmail(invalidEmail);
        });
    }

    @Test
    void creates_a_valid_client_email_when_email_is_valid() throws InvalidClientEmailException {
        final String validEmail = "abc@test.com";
        
        final ClientEmail email = new ClientEmail(validEmail);

        assertEquals(validEmail, email.value());
    }

    private static Stream<Object[]> invalidEmailsProvider() {
        return Stream.of(
            new Object[]{"abc"},
            new Object[]{"abc@"},
            new Object[]{""},
            new Object[]{null},
            new Object[]{"@abc"},
            new Object[]{"@abc@"},
            new Object[]{"abc@test"},
            new Object[]{"abc@test."},
            new Object[]{"com.abc@test."},
            new Object[]{"abc@test,com"}
        );
    }
}
