package com.trapisondastore.trapisondastore.Unit.Client.Domain.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientNameException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientName;

final class ClientNameTest {
    
    @Test
    void exception_is_thrown_when_name_is_empty() {
        final String invalidName = " ";

        assertThrows(InvalidClientNameException.class, () -> {
            new ClientName(invalidName);
        });
    }

    @Test
    void exception_is_thrown_when_name_is_null() {
        final String invalidName = null;

        assertThrows(InvalidClientNameException.class, () -> {
            new ClientName(invalidName);
        });    }

    @Test
    void creates_a_valid_name_with_non_empty_string() throws InvalidClientNameException {
        final String validName = "Rocatagliatta";

        ClientName name = new ClientName(validName);

        assertEquals(validName, name.value());
    }
}
