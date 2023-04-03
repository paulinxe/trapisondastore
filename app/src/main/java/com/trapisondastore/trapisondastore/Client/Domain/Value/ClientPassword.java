package com.trapisondastore.trapisondastore.Client.Domain.Value;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;

public final class ClientPassword {
    private String password;
    private final String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    public ClientPassword(String password) throws InvalidClientPasswordException {
        if (password == null || !password.matches(REGEX)) {
            throw new InvalidClientPasswordException();
        }

        this.password = password;
    }

    public String value() {
        return password;
    }
}
