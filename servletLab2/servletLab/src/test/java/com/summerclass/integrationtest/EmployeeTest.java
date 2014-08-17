package com.summerclass.integrationtest;

import com.summerclass.domain.Employee;
import com.summerclass.domain.Member;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.repository.MemberDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class EmployeeTest extends SpringIntegrationTest
{
    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void testGetEmployees()
    {
        List<Employee> employees = employeeDao.getEmployees();
        assertEquals( employees.size(), employeeDao.getCount() );
        for (Employee employee : employees)
        {
            assertTrue( StringSupport.isGuid( employee.getEmployeeId() ) );
        }
    }
}
