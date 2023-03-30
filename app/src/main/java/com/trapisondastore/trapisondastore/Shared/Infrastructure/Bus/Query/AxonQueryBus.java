package com.trapisondastore.trapisondastore.Shared.Infrastructure.Bus.Query;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.QueryMessage;
import org.axonframework.queryhandling.QueryResponseMessage;
import org.axonframework.queryhandling.SimpleQueryBus;
import org.springframework.context.annotation.Configuration;

import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Query;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.QueryBus;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Response;

@Configuration
public class AxonQueryBus extends SimpleQueryBus implements QueryBus {
    
    public AxonQueryBus(Builder builder) {
        super(builder);
    }

    @Override
    public Response ask(Query query) {
        QueryMessage<Query, List<Response>> queryMessage =
            new GenericQueryMessage(query, ResponseTypes.multipleInstancesOf(Response.class));
        
        CompletableFuture<QueryResponseMessage<List<Response>>> future = this.query(queryMessage);
        
        QueryResponseMessage<List<Response>> response;

        try {
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }

        return response.getPayload().get(0);
    }
}
