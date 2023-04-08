package com.trapisondastore.trapisondastore.Unit.Client.Domain.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientName;

final class ClientNameTest {
    @Test
    void creates_a_valid_name_with_non_empty_string() {
        final String validName = "Rocatagliatta";

        ClientName name = new ClientName(validName);

        assertEquals(validName, name.value().get());
    }
}
