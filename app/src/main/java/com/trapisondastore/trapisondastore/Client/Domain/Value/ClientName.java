package com.trapisondastore.trapisondastore.Client.Domain.Value;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientNameException;

public final class ClientName {
    private String name;

    public ClientName(String name) throws InvalidClientNameException {
        if (name == null || name.isBlank()) {
            throw new InvalidClientNameException();
        }

        this.name = name;
    }

    public String value() {
        return name;
    }
}
