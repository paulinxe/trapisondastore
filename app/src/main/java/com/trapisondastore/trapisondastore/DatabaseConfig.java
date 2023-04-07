package com.trapisondastore.trapisondastore;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig extends DataSourceProperties {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(getDriverClassName());
        dataSourceBuilder.url(getUrl());
        dataSourceBuilder.username(getUsername());
        dataSourceBuilder.password(getPassword());

        return dataSourceBuilder.build();
    }
}