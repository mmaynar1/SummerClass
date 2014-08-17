package com.summerclass.repository;

import com.summerclass.domain.Employee;

import java.util.List;

public interface EmployeeDao
{
    public List<Employee> getEmployees();

    int getCount();

    String getEmployeeId( String firstName, String lastName );
}
