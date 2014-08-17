package com.summerclass.service;

import com.summerclass.domain.Employee;
import com.summerclass.domain.Result;

import java.util.List;

public interface EmployeeService
{
    Result addEmployee( String firstName, String lastName );

    List<Employee> getAll();
}
