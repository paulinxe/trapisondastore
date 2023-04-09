package com.trapisondastore.trapisondastore.Client.Infrastructure.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAClientRepositoryDefinition extends JpaRepository<JPAClient, String> {

    JPAClient findFirstByEmail(String email);
}
