package com.trapisondastore.trapisondastore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.CommandBus;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Query.QueryBus;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Bus.Command.InMemoryCommandBus;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Bus.Query.InMemoryQueryBus;

@Configuration
public class Config {
    
    @Bean
    public QueryBus queryBus() {
        return new InMemoryQueryBus();
    }

    @Bean
    public CommandBus commandBus() {
        return new InMemoryCommandBus();
    }
}
