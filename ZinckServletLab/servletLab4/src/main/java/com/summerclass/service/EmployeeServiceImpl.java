package com.summerclass.service;

import com.summerclass.domain.Employee;
import com.summerclass.domain.Member;
import com.summerclass.domain.Result;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Result> addEmployee( Employee employee )
    {
        List<Result> results = new ArrayList<>();

        for(Result result : employee.validateEditableFields())
        {
            results.add( result );
        }

        if ( new Result().resultsAreValid( results ) )
        {
            String id = employeeDao.createEmployee( employee );
            String successMessage = "Added " + employee.getFirstName() + " " + employee.getLastName() + " with id=" + id;
            results.add( new Result( Result.Status.success, successMessage ) );
        }

        return results;
    }

    @Override
    public Employee getEmployee( String employeeId )
    {
        return employeeDao.getEmployee( employeeId );
    }

    @Override
    public Result updateEmployee( Employee employee )
    {
        Result result;
        try
        {
            //todo validate employee object and throw exception if bad
            if ( StringSupport.isEmptyString( employee.getFirstName() ) ||
                 StringSupport.isEmptyString( employee.getLastName() )  ||
                 StringSupport.isEmptyString( employee.getBarcode() )  ||
                 StringSupport.isEmptyString( employee.getAddress() )  ||
                 StringSupport.isEmptyString( employee.getCity() )  ||
                 StringSupport.isEmptyString( employee.getState() )  ||
                 StringSupport.isEmptyString( employee.getZipCode() )  )
            {
                throw new RuntimeException(  );
            }
            else
            {
                employeeDao.updateEmployee( employee );
            }
            result = new Result( Result.Status.success, "Employee updated successfully" );
        }
        catch (Exception exception)
        {
            result = new Result( Result.Status.failure, "Failed to update" );
        }

        return result;
    }

    @Override
    public List<Employee> getFilteredEmployees( Employee employee )
    {
        return employeeDao.getEmployees( employee );
    }

    @Override
    public int deleteEmployeeEvents( String employeeId )
    {
        int rowsDeleted;
        if(StringSupport.isGuid( employeeId ))
        {
            rowsDeleted = employeeDao.deleteEmployeeEvents(employeeId);
        }
        else
        {
            throw new RuntimeException( "Invalid employeeId" );
        }

        return rowsDeleted;
    }


}
