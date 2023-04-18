package com.trapisondastore.trapisondastore;

import java.io.IOException;
import java.util.Properties;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.core.io.FileSystemResource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile("test")
@EnableTransactionManagement
public class TestDatabaseConfig extends DataSourceProperties {
    LocalSessionFactoryBean sessionFactory;

    public TestDatabaseConfig() {
        
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory()  {
        sessionFactory = new LocalSessionFactoryBean();

        // @TODO: extract to method
        BasicDataSource datasource = new BasicDataSource();
        datasource.setDriverClassName(getDriverClassName());
        datasource.setUrl(getUrl());
        datasource.setUsername(getUsername());
        datasource.setPassword(getPassword());
        sessionFactory.setDataSource(datasource);

        // @TODO: extract to method
        Properties properties = new Properties();
        properties.put(AvailableSettings.HBM2DDL_AUTO, "none");
        properties.put(AvailableSettings.SHOW_SQL, "true"); // @TODO
        properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect"); // @TODO
        sessionFactory.setHibernateProperties(properties);

        sessionFactory.setMappingLocations(
            new FileSystemResource("./src/main/java/com/trapisondastore/trapisondastore/Client/Infrastructure/Persistence/Client.hbm.xml")
        );

        return sessionFactory;
    }

    @Bean("trapisondastore-transaction_manager")
    public PlatformTransactionManager hibernateTransactionManager() throws IOException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());

        return transactionManager;
    }
}
