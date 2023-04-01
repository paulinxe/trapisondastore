package com.trapisondastore.trapisondastore.Client.Domain.Event;

import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;

public class ClientSignedUp extends DomainEvent {
    public final String id;
    public final String email;

    public ClientSignedUp(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
