package com.summerclass.service;

import com.summerclass.domain.Employee;
import com.summerclass.domain.Result;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Result addEmployee( String firstName, String lastName )
    {
        Result result;

        try
        {
            if ( StringSupport.isEmptyString( firstName ) )
            {
                result = new Result( Result.Status.failure, "First Name is a required field." );
            }
            else if ( StringSupport.isEmptyString( lastName ) )
            {
                result = new Result( Result.Status.failure, "Last Name is a required field." );
            }
            else
            {
                String employeeId = employeeDao.createEmployee( firstName, lastName );
                result = new Result( Result.Status.success, "Added " + firstName + " " + lastName + ", id = " + employeeId );
            }
        }
        catch (Exception exception)
        {
            result = new Result( Result.Status.failure, "Internal error: Unable to add employee." );
        }

        return result;
    }

    @Override
    public List<Employee> getAll()
    {
        return employeeDao.getAll();
    }
}
