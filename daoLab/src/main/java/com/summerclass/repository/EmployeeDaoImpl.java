package com.summerclass.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<String> getEmployees( String eventTypeName, int clubNumber )
    {
        String sql = "select distinct concat(e_first_name,\" \",  e_last_name) name from eventSessions es\n" +
                     "join eventTypes et on et.et_id = es.et_id\n" +
                     "join employees e on e.e_id = es.e_id\n" +
                     "join clubs c on c.c_id = es.c_id\n" +
                     "where et_name = :eventTypeName\n" +
                     "and c_number = :clubNumber";

        MapSqlParameterSource source = new MapSqlParameterSource(  );
        source.addValue( "eventTypeName", eventTypeName );
        source.addValue( "clubNumber", clubNumber );

        return jdbc.queryForList( sql, source, String.class );
    }
}
