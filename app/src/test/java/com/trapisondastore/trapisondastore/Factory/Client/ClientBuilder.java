package com.trapisondastore.trapisondastore.Factory.Client;

import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientPasswordException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientId;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientPassword;

public final class ClientBuilder {
    private ClientId id;
    private ClientEmail email;
    private ClientPassword password;

    public ClientBuilder(String id, String email, String password) throws InvalidClientEmailException, InvalidClientIdException, InvalidClientPasswordException {
        this.id = new ClientId(id);
        this.email = new ClientEmail(email);
        this.password = ClientPassword.fromPlain(password);
    }

    public static Client defaultClient() throws InvalidClientEmailException, InvalidClientIdException, InvalidClientPasswordException {
        return new ClientBuilder("f1c5b5e5-68f5-4691-9e9e-0af0e90ebf7a", "test@test.com", "abcdAbcd1").build();
    }

    public Client build() {
        return new Client(id, email, password);
    }
}
