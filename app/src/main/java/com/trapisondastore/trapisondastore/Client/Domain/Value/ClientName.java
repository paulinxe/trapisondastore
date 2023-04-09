package com.trapisondastore.trapisondastore.Client.Domain.Value;

import java.util.Optional;

public final class ClientName {
    private Optional<String> name;

    public ClientName(String name) {
        if (name == null) {
            this.name = Optional.empty();
        } else {
            this.name = Optional.of(name);
        }
    }

    public Optional<String> value() {
        return name;
    }
}
