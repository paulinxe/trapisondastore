package com.trapisondastore.trapisondastore.Shared.Infrastructure;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.SimpleQueryBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trapisondastore.trapisondastore.Client.Application.FindClient;
import com.trapisondastore.trapisondastore.Client.Application.FindClientHandler;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.Response;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Bus.Query.AxonQueryBus;

@Configuration
public class AxonConfig {
    @Bean
    public CommandBus commandBus() {
        return SimpleCommandBus.builder().build();
    }

    @Bean
    public QueryBus queryBus() {
        return new AxonQueryBus(SimpleQueryBus.builder());
    }

    // @Autowired
    // public void registerQueryHandlers(QueryBus queryBus, FindClientHandler queryHandler) {
    //     queryBus.subscribe(FindClient.class.getName(), Response.class, queryHandler);
    // }
}