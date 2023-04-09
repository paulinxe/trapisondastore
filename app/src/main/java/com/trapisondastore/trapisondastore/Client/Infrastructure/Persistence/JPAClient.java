package com.trapisondastore.trapisondastore.Client.Infrastructure.Persistence;

import java.util.UUID;

import com.trapisondastore.trapisondastore.Client.Domain.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class JPAClient {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    private String email;
    private String address;
    private String password;

    public JPAClient() {

    }

    public JPAClient(UUID id, String name, String email, String address, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public JPAClient(Client client) {
        id = client.id().value();
        name = client.name().value().isPresent() ? client.name().value().get() : "";
        email = client.email().value();
        address = client.address().value().isPresent() ? client.address().value().get() : "";
        password = client.password().value();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
}
