package com.summerclass.service;

import com.summerclass.domain.Employee;
import com.summerclass.domain.Result;

import java.util.List;

public interface EmployeeService
{
    List<Result> addEmployee( Employee employee );

    Employee getEmployee( String employeeId );

    Result updateEmployee( Employee employee );

    List<Employee> getFilteredEmployees( Employee employee );

    int deleteEmployeeEvents( String employeeId );
}
