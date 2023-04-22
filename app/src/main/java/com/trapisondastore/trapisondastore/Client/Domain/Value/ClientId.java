package com.trapisondastore.trapisondastore.Client.Domain.Value;

import java.util.UUID;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;

public final class ClientId {
    private UUID value;

    public ClientId() {

    }

    public ClientId(String id) throws InvalidClientIdException {
        try {
            if (id == null) {
                throw new InvalidClientIdException();
            }
            
            this.value = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidClientIdException();
        }
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientId id = (ClientId) o;
        return value.equals(id.value());
    }
}
