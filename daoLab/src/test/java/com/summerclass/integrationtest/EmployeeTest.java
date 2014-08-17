package com.summerclass.integrationtest;

import com.summerclass.domain.EventStatuses;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.repository.EventsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import java.lang.reflect.Method;
import java.util.List;

public class EmployeeTest extends SpringIntegrationTest
{
    @Autowired
    private EventsDao eventsDao;

    @Autowired
    private EmployeeDao employeeDao;

    public static final String TEST_MEMBER_FIRST_NAME = "Mitchum";
    public static final String TEST_EMPLOYEE_FIRST_NAME = "Pam";
    public static final String TEST_EMPLOYEE_LAST_NAME = "Beasley";
    public static final String TEST_EVENT_TYPE = "Test Event";

    @BeforeMethod
    public void beforeMethod( Method method )
    {
        eventsDao.deleteEvents( TEST_MEMBER_FIRST_NAME );
    }

    @Test
    public void testGetEmployees()
    {
        int clubNumber = 3000;
        eventsDao.createEventSession( TEST_MEMBER_FIRST_NAME,
                                      TEST_EMPLOYEE_FIRST_NAME,
                                      EventStatuses.pending.getAbcCode(),
                                      clubNumber,
                                      TEST_EVENT_TYPE );

        List<String> employees = employeeDao.getEmployees( TEST_EVENT_TYPE, clubNumber );
        assertTrue( employees.size() > 0 );
        assertTrue( employees.contains( TEST_EMPLOYEE_FIRST_NAME + " " + TEST_EMPLOYEE_LAST_NAME ) );
    }

}
