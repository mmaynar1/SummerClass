package com.summerclass.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import com.summerclass.controller.SpringInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableAsync
@EnableWebMvc
@PropertySource("database.properties")
@ComponentScan({"com.summerclass"})
public class SpringConfiguration extends WebMvcConfigurerAdapter
{
    @Value("${database}")
    private String database;

    @Value("${id}")
    private String id;

    @Value("${password}")
    private String password;

    public void configureMessageConverters( List<HttpMessageConverter<?>> converters )
    {
        converters.add( jsonConverter() );
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonConverter()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule( new MrBeanModule() );
        objectMapper.enableDefaultTyping( ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, JsonTypeInfo.As.PROPERTY );
        objectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper( objectMapper );
        return converter;
    }

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

    @Override
    public void addInterceptors( InterceptorRegistry registry )
    {
        registry.addInterceptor( getAbcCoreInterceptor() );
    }

    @Bean
    public HandlerInterceptor getAbcCoreInterceptor()
    {
        return new SpringInterceptor();
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
