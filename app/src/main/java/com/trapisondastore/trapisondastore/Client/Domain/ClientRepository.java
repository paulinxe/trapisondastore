package com.trapisondastore.trapisondastore.Client.Domain;

import java.util.Optional;

import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientId;

public interface ClientRepository {
    public Optional<Client> findByEmail(ClientEmail email);
    public Optional<Client> findById(ClientId id);
    public void save(Client client);
}
