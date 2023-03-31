package com.trapisondastore.trapisondastore.Shared.Infrastructure.Bus.Query;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.annotation.Bean;

import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Query;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.QueryBus;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.QueryHandler;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Response;

public class InMemoryQueryBus implements QueryBus {

    @Override
    public Response ask(Query query) {
        final String handlerName = query.getClass().getName() + "Handler";

        try {
            Class<?> handler = Class.forName(handlerName);
            Constructor<?> constructor = handler.getDeclaredConstructor();
            Object handlerObject = constructor.newInstance();
            return ((QueryHandler) handlerObject).handle(query);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            // @TODO: Declare a custom exception.
            e.printStackTrace();
        }

        return null;
    }
    
}
