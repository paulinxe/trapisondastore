package com.trapisondastore.trapisondastore.Client.Domain.Value;

import java.util.Optional;

public final class ClientAddress {
    private Optional<String> address;

    public ClientAddress(String address) {
        this.address = Optional.of(address);
    }

    public Optional<String> value() {
        return address;
    }
}
