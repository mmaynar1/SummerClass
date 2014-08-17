package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectMapper implements RowMapper<Object>
{
    private String className;

    public ObjectMapper( String className )
    {
        this.className = className;
    }

    public Object mapRow( ResultSet resultSet, int rowNumber ) throws SQLException
    {
        return getObject( resultSet );
    }

    private Object getObject( ResultSet resultSet )
    {
        Object object = null;
        try
        {
            object = Class.forName( className ).newInstance();

            for (Field field : object.getClass().getDeclaredFields())
            {
                //field names must match aliases in queries
                setValue( object, field.getName(), resultSet.getString( field.getName() ) );
            }
        }
        catch (Exception exception)
        {
            //ignore it
        }

        return object;
    }

    private static void setValue( Object object, String fieldName, Object value ) throws Exception
    {
        Class[] arguments = {value.getClass()};
        String methodName = "set" + fieldName.substring( 0, 1 ).toUpperCase() + fieldName.substring( 1 );
        try
        {
            Method method = object.getClass().getMethod( methodName, arguments );

            method.invoke( object, value );
        }
        catch (NoSuchMethodException exception)
        {
            // ignore it
        }
    }
}

