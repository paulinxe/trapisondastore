package com.trapisondastore.trapisondastore.Client.Infrastructure;

import com.trapisondastore.trapisondastore.Client.Application.FindClient;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Query;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.QueryBus;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Response;

public class ClientController {
    
    private QueryBus queryBus;

    public ClientController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    public String test() {
        Query query = new FindClient("1");

        Response response = queryBus.ask(query);

        return response.toString();
    }
}
