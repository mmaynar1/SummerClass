package com.summerclass.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringExtractor implements ResultSetExtractor<String>
{
    @Override
    public String extractData( ResultSet resultSet ) throws SQLException, DataAccessException
    {
        String result = null;

        if ( resultSet.next() )
        {
            result = resultSet.getString( 1 );
        }

        return result;
    }
}
