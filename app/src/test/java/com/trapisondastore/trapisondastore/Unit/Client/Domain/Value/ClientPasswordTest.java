package com.trapisondastore.trapisondastore.Unit.Client.Domain.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientPassword;

public class ClientPasswordTest {

    @Test
    void exception_is_thrown_when_password_is_empty() {
        String password = "";

        assertThrows(InvalidClientPasswordException.class, () -> {
            new ClientPassword(password);
        });
    }

    @Test
    void exception_is_thrown_when_password_is_null() {
        String password = null;

        assertThrows(InvalidClientPasswordException.class, () -> {
            new ClientPassword(password);
        });
    }

    @Test
    void exception_is_thrown_when_password_only_contains_letters() {
        String password = "supersecure";

        assertThrows(InvalidClientPasswordException.class, () -> {
            new ClientPassword(password);
        });
    }

    @Test
    void exception_is_thrown_when_password_only_contains_numbers() {
        String password = "13371337";

        assertThrows(InvalidClientPasswordException.class, () -> {
            new ClientPassword(password);
        });
    }

    @Test
    void exception_is_thrown_when_password_is_shorter_than_8_characters() {
        String password = "aBcd12";

        assertThrows(InvalidClientPasswordException.class, () -> {
            new ClientPassword(password);
        });
    }

    @Test
    void exception_is_thrown_when_password_contains_upper_and_lower_case_but_no_numbers() {
        String password = "aBcdEfgKw";

        assertThrows(InvalidClientPasswordException.class, () -> {
            new ClientPassword(password);
        });
    }

    @Test
    void password_is_set_when_valid() throws InvalidClientPasswordException {
        String expected = "SuperSecure69";

        ClientPassword password = new ClientPassword(expected);

        assertEquals(expected, password.value());
    }
}
