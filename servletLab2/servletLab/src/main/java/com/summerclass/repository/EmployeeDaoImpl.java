package com.summerclass.repository;

import com.summerclass.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Employee> getEmployees()
    {
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.query( Employee.Mapper.QUERY, source, new Employee.Mapper() );
    }

    @Override
    public int getCount()
    {
        String sql = "select count(*) from employees";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, Integer.class );
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
