package com.summerclass.integrationtest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestConfiguration
{
    @Bean
    public ObjectMapper jsonSerializer()
    {
       ObjectMapper objectMapper = new ObjectMapper();
       objectMapper.registerModule(new MrBeanModule());
       objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, JsonTypeInfo.As.PROPERTY);
       return objectMapper;
    }

    @Bean
    @Autowired
    public MockMvc mockMvc(WebApplicationContext applicationContext)
    {
       DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(applicationContext);

       builder.alwaysExpect(MockMvcResultMatchers.status().isOk());

       builder.defaultRequest(MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

       return builder.build();
    }
}
