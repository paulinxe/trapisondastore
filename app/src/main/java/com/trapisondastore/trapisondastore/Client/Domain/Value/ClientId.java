package com.trapisondastore.trapisondastore.Client.Domain.Value;

import java.util.UUID;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;

public final class ClientId {
    private UUID id;

    public ClientId(String id) throws InvalidClientIdException {
        try {
            if (id == null) {
                throw new InvalidClientIdException();
            }
            
            this.id = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidClientIdException();
        }
    }

    public UUID value() {
        return id;
    }
}
