package com.trapisondastore.trapisondastore.Client.Domain;

import com.trapisondastore.trapisondastore.Client.Domain.Event.ClientSignedUp;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
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

    public Client(ClientId id, ClientName name, ClientEmail email, ClientPassword password, ClientAddress address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public ClientPassword password() {
        return password;
    }

    public void updatePassword(String password) throws InvalidClientPasswordException {
        this.password = ClientPassword.fromEncrypted(password);
        // @TODO: publish domain event.
    }

    public static Client fromPersistence(String id, String name, String email, String address)
            throws InvalidClientIdException, InvalidClientEmailException {
        return new Client(
            new ClientId(id),
            new ClientName(name),
            new ClientEmail(email),
            null,
            new ClientAddress(address)
        );
    }

    public static Client signUp(String id, String email, String password)
            throws InvalidClientIdException, InvalidClientEmailException, InvalidClientPasswordException {
        Client client = new Client(
            new ClientId(id),
            new ClientName(null),
            new ClientEmail(email),
            ClientPassword.fromEncrypted(password),
            new ClientAddress(null)
        );

        client.registerEvent(new ClientSignedUp(client.id().value().toString(), client.email().value()));

        return client;
    }
}
