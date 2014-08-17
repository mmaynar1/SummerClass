package com.summerclass.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.lang.*;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.Throwable;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ControllerDaoImpl implements ControllerDao
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public MvcResult doRequest( HttpMethod method, String url, Map<String, String> params )
    {
        try
        {
            Object body = null;
            return mockMvc.perform( buildRequest( method, url, params, body ) ).andReturn();
        }
        catch (Throwable exception)
        {
            throw new java.lang.RuntimeException( exception );
        }
    }

    @Override
    public MvcResult doRequest( HttpMethod method, String url, Map<String, String> params, Object body )
    {
        try
        {
            MvcResult result = mockMvc.perform( buildRequest( method, url, params, body ) ).andReturn();
            return result;
        }
        catch (Throwable exception)
        {
            throw new RuntimeException( exception );
        }
    }

    @Override
    public <T> T doRequest( HttpMethod method, String url, Map<String, String> params, Object body, Class<T> clazz )
    {
        T response = null;
        MvcResult result = doRequest( method, url, params, body );
        if ( clazz != null )
        {
            response = deserialize( result.getResponse().getContentAsByteArray(), clazz );
        }
        return response;
    }

    @Override
    public Map<String, String> createParameters( String... parameters )
    {
        Map<String, String> urlParameters = new HashMap<String, String>();
        for (int i = 0; i < parameters.length; i += 2)
        {
            urlParameters.put( parameters[i], parameters[i + 1] );
        }
        return urlParameters;
    }

    @Override
    public Map<String, Object> createObjectParameters()
    {
        Map<String, Object> parameters = new HashMap<String, Object>();
        return parameters;
    }

    private String getString( MvcResult result )
    {
        try
        {
            return result.getResponse().getContentAsString();
        }
        catch (Exception exception)
        {
            throw new RuntimeException( exception );
        }
    }

    @Override
    public MockHttpServletRequestBuilder buildRequest( HttpMethod method, String url, Map<String, String> params, Object body )
    {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.request( method, url );

        for (Map.Entry<String, String> entry : params.entrySet())
        {
            builder.param( entry.getKey(), entry.getValue() );
        }

        builder.content( serialize( body ) );

        return builder;
    }

    private byte[] serialize( Object object )
    {
        try
        {
            return objectMapper.writeValueAsBytes( object );
        }
        catch (Exception exception)
        {
            throw new RuntimeException( exception );
        }
    }

    private <T> T deserialize( byte[] json, Class<T> clazz )
    {
        try
        {
            return objectMapper.readValue( json, clazz );
        }
        catch (Exception exception)
        {
            throw new RuntimeException( exception );
        }
    }
}
