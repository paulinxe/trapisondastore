package com.trapisondastore.trapisondastore.Client.Domain.Event;

import java.util.HashMap;
import com.trapisondastore.trapisondastore.Shared.Domain.DomainEvent;

public class ClientSignedUp extends DomainEvent {
    public ClientSignedUp() {

    }
    
    public ClientSignedUp(String id, String email) {
        super(
            new HashMap<String, Object>() {{
                put("id", id);
                put("email", email);
            }},
            "trapisondastore.client.1.event.client.signed_up"
        );
    }
}
