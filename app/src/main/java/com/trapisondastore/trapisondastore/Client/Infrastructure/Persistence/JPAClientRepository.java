package com.trapisondastore.trapisondastore.Client.Infrastructure.Persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;
import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientEmail;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.MySQLRepository;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception.UnableToBuildAggregateRootException;
import jakarta.transaction.Transactional;

@Service
@Repository
public class JPAClientRepository extends MySQLRepository implements ClientRepository {
    
    @Autowired
    private JPAClientRepositoryDefinition jpaRepository;

    @Override
    public Optional<Client> findByEmail(ClientEmail email) throws UnableToBuildAggregateRootException {
        var client = jpaRepository.findFirstByEmail(email.value());
        if (client == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(
                Client.fromPersistence(
                    client.getId().toString(),
                    client.getName(),
                    client.getEmail(),
                    client.getAddress()
                )
            );
        } catch (InvalidClientIdException | InvalidClientEmailException e) {
            throw new UnableToBuildAggregateRootException(e);
        }
    }

    @Transactional
    @Override
    public void save(Client client) {
        JPAClient jpaClient = new JPAClient(client);
        jpaRepository.save(jpaClient);
        registerDomainEvents(client);
    }
}
