package com.summerclass.repository;

import java.util.List;

public interface EmployeeDao
{
    List<String> getEmployeeNames( String eventTypeId, String clubId );

    int getEventsCount( String employeeId );

    void deleteEvents( String employeeId );

    String getEmployeeId( String firstName, String lastName );
}
