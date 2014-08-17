package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee
{
    private String employeeId;
    private String firstName;
    private String lastName;

    public Employee()
    {
    }

    public static class Mapper implements RowMapper<Employee>
    {
        public final static String QUERY = "select e.e_id, e.e_first_name, e.e_last_name " +
                                           "from employees e ";

        public Employee mapRow( ResultSet resultSet, int rowNumber ) throws SQLException
        {
            Employee employee = new Employee();

            employee.setEmployeeId( resultSet.getString( "E_ID" ) );
            employee.setFirstName( resultSet.getString( "E_FIRST_NAME" ) );
            employee.setLastName( resultSet.getString( "E_LAST_NAME" ) );
            return employee;
        }
    }

    public IdName getIdName()
    {
        return new IdName( getEmployeeId(), (getFirstName() + " " + getLastName()) );
    }

    public String getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId( String employeeId )
    {
        this.employeeId = employeeId;
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
}