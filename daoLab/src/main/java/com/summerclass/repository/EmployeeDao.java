package com.summerclass.repository;

import java.util.List;

public interface EmployeeDao
{
    List<String> getEmployees( String eventTypeName, int clubNumber );
}
