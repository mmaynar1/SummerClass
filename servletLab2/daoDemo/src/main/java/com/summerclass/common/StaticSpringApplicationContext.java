package com.summerclass.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class StaticSpringApplicationContext implements ApplicationContextAware
{
    private static ApplicationContext context;

    public void setApplicationContext( ApplicationContext context ) throws BeansException
    {
        StaticSpringApplicationContext.context = context;
    }

    public static <T> T getSpringObject( Class<T> type )
    {
        return context.getBean( type );
    }
}
