package com.summerclass.integrationtest;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public interface ControllerDao
{
    MvcResult doRequest( HttpMethod method, String url, Map<String, String> params );

    MvcResult doRequest( HttpMethod method, String url, Map<String, String> params, Object body );

    <T> T doRequest( HttpMethod method, String url, Map<String, String> params, Object body, Class<T> clazz );

    Map<String, String> createParameters( String... parameters );

    Map<String, Object> createObjectParameters();

    MockHttpServletRequestBuilder buildRequest( HttpMethod method, String url, Map<String, String> params, Object body );
}
