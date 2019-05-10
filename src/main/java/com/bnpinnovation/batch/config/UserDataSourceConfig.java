package com.bnpinnovation.batch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "helloEntityManagerFactory",
        transactionManagerRef = "helloTransactionManager",
        basePackages = {"com.bnpinnovation.batch.repository"})
public class UserDataSourceConfig {
    @Bean(name = "helloDataSource")
    @ConfigurationProperties(prefix = "hello.datasource")
    public DataSource helloDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "helloEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean helloEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(helloDataSource()).packages("com.bnpinnovation.batch.domain").persistenceUnit("user")
                .build();
    }

    @Bean(name = "helloTransactionManager")
    public PlatformTransactionManager helloTransactionManager(
            @Qualifier("helloEntityManagerFactory") EntityManagerFactory helloEntityManagerFactory) {
        return new JpaTransactionManager(helloEntityManagerFactory);
    }

}
