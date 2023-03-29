package com.trapisondastore.trapisondastore.Unit.Client.Domain.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientId;

final class ClientIdTest {
    
    @Test
    void exception_is_thrown_when_id_is_not_uuid() {
        final String invalidUuid = "abc";

        assertThrows(InvalidClientIdException.class, () -> {
            new ClientId(invalidUuid);
        });
    }

    @Test
    void exception_is_thrown_when_id_is_null() {
        final String invalidUuid = null;

        assertThrows(InvalidClientIdException.class, () -> {
            new ClientId(invalidUuid);
        });
    }

    @Test
    void creates_a_valid_id_with_valid_uuid() throws InvalidClientIdException {
        final String validUuid = UUID.randomUUID().toString();
        
        ClientId id = new ClientId(validUuid);

        assertEquals(validUuid, id.value().toString());
    }
}
