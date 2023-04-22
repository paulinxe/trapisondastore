package com.trapisondastore.trapisondastore.Client.Domain.Value;

import java.util.Optional;

public final class ClientAddress {
    private String value;

    public ClientAddress() {

    }

    public ClientAddress(String address) {
        value = address;
    }

    public Optional<String> value() {
        return Optional.ofNullable(value);
    }
}
