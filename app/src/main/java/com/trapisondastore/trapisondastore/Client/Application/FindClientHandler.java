package com.trapisondastore.trapisondastore.Client.Application;

import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Query;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.QueryHandler;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Response;

public class FindClientHandler implements QueryHandler {
    
    class FindClientResponse implements Response {
        public final String test;

        public FindClientResponse(String test) {
            this.test = test;
        }
    }

    @org.axonframework.queryhandling.QueryHandler
    @Override
    public Response handle(Query query) {
        return new FindClientResponse("abc");
    }
}
