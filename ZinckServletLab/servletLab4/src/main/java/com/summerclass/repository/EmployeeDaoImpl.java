package com.summerclass.repository;

import com.summerclass.domain.Employee;
import com.summerclass.domain.EventSession;
import com.summerclass.domain.IdName;
import com.summerclass.domain.ObjectMapper;
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
    public List<Employee> getAllEmployees()
    {
        String sql = Employee.Mapper.QUERY;
        MapSqlParameterSource source = new MapSqlParameterSource();
        sql += " order by upper( e.e_last_name ), upper( e.e_first_name ), e.e_id";

        return jdbc.query( sql, source, new Employee.Mapper() );
    }

    @Override
    public List<Object> getAllEmployeesIdName()
    {
        String sql = "Select e_id as ID, concat_ws(' ', e_first_name, e_last_name) as NAME from employees";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.query( sql, source, new ObjectMapper( "com.summerclass.domain.IdName") );
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
    public String createEmployee( Employee employee )
    {
        String employeeId = RandomGenerator.getGuid();
        String sql = "insert into employees (e_id, e_first_name, e_last_name, e_barcode, e_address, e_city, e_state, e_zip_code, e_hourly_wage, e_start_date ) " +
                     "values (:id, :firstName, :lastName, :barcode, :address, :city, :state, :zipCode, :hourlyWage, now() )";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "id", employeeId );
        source.addValue( "firstName", employee.getFirstName() );
        source.addValue( "lastName", employee.getLastName() );
        source.addValue( "barcode", employee.getBarcode() );
        source.addValue( "address", employee.getAddress() );
        source.addValue( "city", employee.getCity() );
        source.addValue( "state", employee.getState() );
        source.addValue( "zipCode", employee.getZipCode() );
        source.addValue( "hourlyWage", employee.getHourlyWage() );

        jdbc.update( sql, source );

        return employeeId;
    }

    @Override
    public String getEmployeeId( String firstName, String lastName )
    {

        String sql = "select e_id from employees where e_first_name = :firstName and e_last_name = :lastName";

        MapSqlParameterSource source = new MapSqlParameterSource( "firstName", firstName );
        source.addValue( "lastName", lastName );

        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public Employee getEmployee( String employeeId )
    {
        String sql = Employee.Mapper.QUERY + " where e_id = :employeeId";
        MapSqlParameterSource source = new MapSqlParameterSource( "employeeId", employeeId );
        return jdbc.queryForObject( sql, source, new Employee.Mapper() );
    }

    @Override
    public void updateEmployee( Employee employee )
    {
        String sql = "update employees " +
                     "set e_first_name = :firstName, " +
                     "e_last_name = :lastName, " +
                     "e_barcode=:barcode, " +
                     "e_address=:address, " +
                     "e_city=:city, " +
                     "e_state=:state, " +
                     "e_zip_code=:zipCode, " +
                     "e_hourly_wage=:hourlyWage " +
                     "where e_id=:employeeId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "firstName", employee.getFirstName() );
        source.addValue( "lastName", employee.getLastName() );
        source.addValue( "barcode", employee.getBarcode() );
        source.addValue( "address", employee.getAddress() );
        source.addValue( "city", employee.getCity() );
        source.addValue( "state", employee.getState() );
        source.addValue( "zipCode", employee.getZipCode() );
        source.addValue( "hourlyWage", employee.getHourlyWage() );
        source.addValue( "employeeId", employee.getEmployeeId() );

        jdbc.update( sql, source );
    }

    @Override
    public List<Employee> getEmployees( Employee employee )
    {
        String sql = Employee.Mapper.QUERY + " where 1 = 1 ";
        MapSqlParameterSource source = new MapSqlParameterSource();

        if ( !employee.getFirstName().isEmpty() )
        {
            sql += " and e.e_first_name like :firstName";
            source.addValue( "firstName", "%" + employee.getFirstName() + "%" );
        }
        if ( !employee.getLastName().isEmpty() )
        {
            sql += " and e.e_last_name like :lastName";
            source.addValue( "lastName", "%" + employee.getLastName() + "%" );
        }
        if ( !employee.getBarcode().isEmpty() )
        {
            sql += " and e.e_barcode like :barcode";
            source.addValue( "barcode", "%" + employee.getBarcode() + "%" );
        }
        if ( !employee.getAddress().isEmpty() )
        {
            sql += " and e.e_address like :address";
            source.addValue( "address", "%" + employee.getAddress() + "%" );
        }
        if ( !employee.getCity().isEmpty() )
        {
            sql += " and e.e_city like :city";
            source.addValue( "city", "%" + employee.getCity() + "%" );
        }
        if ( !employee.getState().isEmpty() )
        {
            sql += " and e.e_state like :state";
            source.addValue( "state", "%" + employee.getState() + "%" );
        }
        if ( !employee.getZipCode().isEmpty() )
        {
            sql += " and e.e_zip_code like :zipCode";
            source.addValue( "zipCode", "%" + employee.getZipCode() + "%" );
        }
        if ( !employee.getHourlyWage().isEmpty()   )
        {
            sql += " and e.e_hourly_wage like :hourlyWage";
            source.addValue( "hourlyWage", "%" + employee.getHourlyWage() + "%" );
        }

        return jdbc.query( sql, source, new Employee.Mapper() );
    }

    @Override
    public int deleteEmployeeEvents( String employeeId )
    {
        String sql = "delete from eventSessions where e_id = :employeeId";
        MapSqlParameterSource source = new MapSqlParameterSource("employeeId", employeeId );
        return jdbc.update( sql, source );
    }
}
