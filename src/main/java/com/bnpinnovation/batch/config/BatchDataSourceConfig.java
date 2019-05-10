package com.bnpinnovation.batch.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class BatchDataSourceConfig {
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

//    @Bean(name = "batchEntityManagerFactory")
//    @Primary
//    public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
//            EntityManagerFactoryBuilder builder, @Qualifier("batchDataSource") DataSource dataSource) {
//        return builder.dataSource(dataSource).build();
//    }
//
//    @Bean(name = "batchTransactionManager")
//    public PlatformTransactionManager barTransactionManager(
//            @Qualifier("batchEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
//        return new JpaTransactionManager(barEntityManagerFactory);
//    }

}
