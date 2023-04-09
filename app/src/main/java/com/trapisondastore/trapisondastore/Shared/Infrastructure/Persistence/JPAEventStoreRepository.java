package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAEventStoreRepository extends JpaRepository<JPAEventStore, String> {
    
}
