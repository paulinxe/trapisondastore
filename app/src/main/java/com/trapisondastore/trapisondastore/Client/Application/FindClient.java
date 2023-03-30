package com.trapisondastore.trapisondastore.Client.Application;

import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Query;

public class FindClient implements Query {
    public final String id;

    public FindClient(String id) {
        this.id = id;
    }
}
