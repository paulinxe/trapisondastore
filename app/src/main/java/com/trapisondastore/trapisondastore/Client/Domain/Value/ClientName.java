package com.trapisondastore.trapisondastore.Client.Domain.Value;

import java.util.Optional;

public final class ClientName {
    private String value;

    public ClientName() {
        
    }

    public ClientName(String name) {
        value = name;
    }

    public Optional<String> value() {
        return Optional.ofNullable(value);
    }
}
