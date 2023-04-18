package com.trapisondastore.trapisondastore.Client.Domain.Value;

import java.util.UUID;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;

public final class ClientId {
    private String value;

    public ClientId() {

    }

    public ClientId(String id) throws InvalidClientIdException {
        try {
            if (id == null) {
                throw new InvalidClientIdException();
            }
            
            this.value = UUID.fromString(id).toString();
        } catch (IllegalArgumentException e) {
            throw new InvalidClientIdException();
        }
    }

    public String value() {
        return value;
    }
}
