package com.trapisondastore.trapisondastore.Client.Domain.Read;

import com.trapisondastore.trapisondastore.Client.Domain.Value.ClientId;

public interface ClientReadRepository {
    public ClientRead find(ClientId id);
}
