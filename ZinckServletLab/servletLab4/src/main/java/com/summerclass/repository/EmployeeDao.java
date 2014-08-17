package com.summerclass.repository;

import com.summerclass.domain.Employee;
import com.summerclass.domain.IdName;

import java.util.List;

public interface EmployeeDao
{
    List<String> getEmployeeIds( String firstName );

    List<Employee> getAllEmployees();

    List<Object> getAllEmployeesIdName();

    String createEmployee( String firstName, String lastName );

    String createEmployee( Employee employee );

    String getEmployeeId( String firstName, String lastName );

    Employee getEmployee( String employeeId );

    void updateEmployee( Employee employee );

    List<Employee> getEmployees( Employee employee );

    int deleteEmployeeEvents( String employeeId );
}
