package com.summerclass.repository;

import com.summerclass.domain.Employee;
import com.summerclass.domain.Selectable;
import com.summerclass.domain.IdName;
import com.summerclass.utility.RandomGenerator;
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
    public List<String> getEmployeeIds( String firstName )
    {
        String sql = "select e_id from employees where e_first_name = :firstName order by upper( e_first_name )";

        MapSqlParameterSource source = new MapSqlParameterSource( "firstName", firstName );

        return jdbc.queryForList( sql, source, String.class );
    }

    @Override
    public String createEmployee( String firstName, String lastName )
    {
        String employeeId = RandomGenerator.getGuid();
        String sql = "insert into employees (e_id, e_first_name, e_last_name) values (:id, :firstName, :lastName )";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "firstName", firstName );
        source.addValue( "lastName", lastName );
        source.addValue( "id", employeeId );

        jdbc.update( sql, source );

        return employeeId;
    }

    @Override
    public Selectable getRandomEmployee()
    {
        String sql = "select e.e_id id, concat_ws( ' ', e.e_first_name, e.e_last_name ) name " +
                     "from employees e order by rand() limit 1";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.queryForObject( sql, source, new IdName.Mapper() );
    }

    @Override
    public List<Employee> getAll()
    {
        String sql = Employee.Mapper.QUERY + " order by upper( e_first_name ), upper( e_last_name ), e_id";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.query( sql, source, new Employee.Mapper() );
    }
}
