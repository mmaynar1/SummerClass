package com.summerclass.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@PropertySource("database.properties")
@ComponentScan({"com.summerclass"})
public class SpringConfiguration
{
    @Value("${database}")
    private String database;

    @Value("${id}")
    private String id;

    @Value("${password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public NamedParameterJdbcTemplate jdbc()
    {
        return new NamedParameterJdbcTemplate( dataSource() );
    }

    @Bean
    public PlatformTransactionManager transactionManager()
    {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource( dataSource() );
        manager.setNestedTransactionAllowed( true );
        return manager;
    }

    @Bean
    public DataSource dataSource()
    {
        return new DriverManagerDataSource( database, id, password );
    }
}
