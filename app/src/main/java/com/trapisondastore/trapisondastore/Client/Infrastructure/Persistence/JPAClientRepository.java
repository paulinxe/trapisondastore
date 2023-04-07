package com.trapisondastore.trapisondastore.Client.Infrastructure.Persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientNameException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception.UnableToBuildAggregateRootException;

@Service
public class JPAClientRepository implements ClientRepository {
    
    @Autowired
    private JPAClientRepositoryDefinition jpaRepository;

    @Override
    public Optional<Client> findByEmail(ClientEmail email) throws UnableToBuildAggregateRootException {
        var client = jpaRepository.findFirstByEmail(email.value());
        if (client == null) {
            return Optional.of(null);
        }

        try {
            return Optional.of(
                Client.fromPersistence(
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getAddress()
                )
            );
        } catch (InvalidClientIdException | InvalidClientNameException | InvalidClientEmailException e) {
            throw new UnableToBuildAggregateRootException(e);
        }
    }

    @Override
    public void save(Client client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}
