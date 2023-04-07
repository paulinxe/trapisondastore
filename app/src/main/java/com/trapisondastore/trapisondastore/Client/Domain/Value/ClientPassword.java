package com.trapisondastore.trapisondastore.Client.Domain.Value;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;

public final class ClientPassword {
    private String password;
    private final static String REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

    private ClientPassword(String password) {
        this.password = password;
    }

    public String value() {
        return password;
    }

    public static ClientPassword fromPlain(String plain) throws InvalidClientPasswordException {
        if (plain == null || !plain.matches(REGEX)) {
            throw new InvalidClientPasswordException();
        }

        return new ClientPassword(plain);
    }

    public static ClientPassword fromEncrypted(String encrypted) throws InvalidClientPasswordException {
        if (encrypted == null) {
            throw new InvalidClientPasswordException();
        }
        
        return new ClientPassword(encrypted);
    }
}
