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
    public List<String> getEmployeeNames( String eventTypeId, String clubId )
    {
        String sql = "select distinct concat(e_first_name,\" \",  e_last_name) name " +
                     "from eventSessions es " +
                     "join eventTypes et on et.et_id = es.et_id " +
                     "join employees e on e.e_id = es.e_id " +
                     "join clubs c on c.c_id = es.c_id " +
                     "where et.et_id = :eventTypeId " +
                     "and c.c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "eventTypeId", eventTypeId );
        source.addValue( "clubId", clubId );

        return jdbc.queryForList( sql, source, String.class );
    }

    @Override
    public int getEventsCount( String employeeId )
    {
        String sql = "select count(*) " +
                     "from eventSessions es " +
                     "where es.e_id = :employeeId";

        MapSqlParameterSource source = new MapSqlParameterSource( "employeeId", employeeId );

        return jdbc.queryForObject( sql, source, Integer.class );
    }


    @Override
    public void deleteEvents( String employeeId )
    {
        String sql = "delete from " +
                     "eventSessions " +
                     "where e_id = :employeeId ";

        MapSqlParameterSource source = new MapSqlParameterSource( "employeeId", employeeId );

        jdbc.update( sql, source );
    }

    @Override
    public String getEmployeeId( String firstName, String lastName )
    {
        String sql = "select e_id from employees " +
                     "where e_first_name = :firstName " +
                     "and e_last_name = :lastName";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "firstName", firstName );
        source.addValue( "lastName", lastName );

        return jdbc.query( sql, source, new StringExtractor() );
    }

}
