package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;
import java.io.Serializable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee implements Serializable
{
    private String firstName;
    private String lastName;
    private String id;

    public Employee()
    {
    }

    public static class Mapper implements RowMapper<Employee>
    {
        public final static String QUERY = "select e_id, e_first_name, e_last_name from employees";

        @Override
        public Employee mapRow( ResultSet resultSet, int rowNum ) throws SQLException
        {
            Employee employee = new Employee();
            employee.setId( resultSet.getString( "E_ID" ) );
            employee.setFirstName( resultSet.getString( "E_FIRST_NAME" ) );
            employee.setLastName( resultSet.getString( "E_LAST_NAME" ) );
            return employee;
        }
    }

    @Override
    public String toString()
    {
        return "Employee{" +
               "firstName='" + getFirstName() + '\'' +
               ", lastName='" + getLastName() + '\'' +
               '}';
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }
}
