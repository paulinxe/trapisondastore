package com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query;

public interface QueryBus {
    public Response ask(Query query);
}
