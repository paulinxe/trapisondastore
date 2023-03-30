package com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query;

public interface QueryHandler {
    public Response handle(Query query);
}
