package com.summerclass.repository;

import com.summerclass.domain.Selectable;

import java.util.List;

public interface EmployeeDao
{
    List<String> getEmployeeIds( String firstName );

    String createEmployee( String firstName, String lastName );

    Selectable getRandomEmployee();
}
