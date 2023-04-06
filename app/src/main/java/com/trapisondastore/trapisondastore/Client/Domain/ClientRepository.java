package com.trapisondastore.trapisondastore.Client.Domain;

import java.util.Optional;

import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception.UnableToBuildAggregateRootException;

public interface ClientRepository {
    public Optional<Client> findByEmail(ClientEmail email) throws UnableToBuildAggregateRootException;
    public void save(Client client);
}
