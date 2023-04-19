package com.trapisondastore.trapisondastore;

import java.io.File;
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
public class TestHibernateConfig extends DataSourceProperties {
    LocalSessionFactoryBean sessionFactory;

    public TestHibernateConfig() {

    }

    private void setHibernateXMLFiles() {
        File directory = new File("./src/main/java/com/trapisondastore/trapisondastore/");
        File[] files = directory.listFiles();

        for (File file : files) {
            if (!file.isDirectory()) {
                continue;
            }

            File persistenceDirectory = new File(
                file.getAbsolutePath() + "/Infrastructure/Persistence"
            );

            for (File f : persistenceDirectory.listFiles()) {
                if (f.getName().endsWith("hbm.xml")) {
                    sessionFactory.setMappingLocations(new FileSystemResource(f.getAbsolutePath()));
                }
            }
        }

    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
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

        setHibernateXMLFiles();

        return sessionFactory;
    }

    @Bean("trapisondastore-transaction_manager")
    public PlatformTransactionManager hibernateTransactionManager() throws IOException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory.getObject());

        return transactionManager;
    }
}
