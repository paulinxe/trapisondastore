package com.trapisondastore.trapisondastore.Client.Domain;

import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientAddress;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientId;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientName;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientPassword;
import com.trapisondastore.trapisondastore.Shared.Domain.AggregateRoot;

public final class Client extends AggregateRoot {
    private ClientId id;
    private ClientName name;
    private ClientEmail email;
    private ClientAddress address;
    private ClientPassword password;

    public Client(ClientId id, ClientName name, ClientEmail email, ClientAddress address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public ClientId id() {
        return id;
    }

    public ClientName name() {
        return name;
    }

    public ClientEmail email() {
        return email;
    }

    public ClientAddress address() {
        return address;
    }
}
