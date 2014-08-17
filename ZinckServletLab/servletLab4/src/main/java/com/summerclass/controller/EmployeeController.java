package com.summerclass.controller;

import com.summerclass.domain.Employee;
import com.summerclass.domain.EmployeeBean;
import com.summerclass.domain.Member;
import com.summerclass.domain.Result;
import com.summerclass.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EmployeeController
{
    @Autowired
    EmployeeBean employeeBean;

    @Autowired
    EmployeeService employeeService;

    public static final String ADD_EMPLOYEE1_URL = "/addEmployee1";
    public static final String ADD_EMPLOYEE2_URL = "/addEmployee2";
    public static final String ADD_EMPLOYEE3_URL = "/addEmployee3";

    public static final String VIEW_EMPLOYEES_URL = "/viewEmployees";
    public static final String EDIT_EMPLOYEE_URL = "/editEmployee";
    public static final String ERROR_URL = "jsp/error.jsp";

    @RequestMapping(value = ADD_EMPLOYEE1_URL, method = RequestMethod.GET)
    public ModelAndView startAddEmployee( HttpServletRequest request )
    {
        ModelAndView modelAndView;

        employeeBean.setEmployee( new Employee() );
        modelAndView = new ModelAndView( "jsp/addEmployee1.jsp", "employee", employeeBean.getEmployee() );

        return modelAndView;
    }

    @RequestMapping(value = ADD_EMPLOYEE1_URL, method = RequestMethod.POST)
    public ModelAndView AddEmployee2( @ModelAttribute("employee") Employee employee, HttpServletRequest request )
    {
        ModelAndView modelAndView;

        employeeBean.getEmployee().setFirstName( employee.getFirstName() );
        employeeBean.getEmployee().setLastName( employee.getLastName() );
        employeeBean.getEmployee().setBarcode( employee.getBarcode() );
        modelAndView = new ModelAndView( "jsp/addEmployee2.jsp", "employee", employeeBean.getEmployee() );


        return modelAndView;
    }

    @RequestMapping(value = ADD_EMPLOYEE2_URL, method = RequestMethod.POST)
    public ModelAndView AddEmployee3( @ModelAttribute("employee") Employee employee,
                                      @RequestParam boolean previousPage,
                                      HttpServletRequest request )
    {
        ModelAndView modelAndView;

        employeeBean.getEmployee().setAddress( employee.getAddress() );
        employeeBean.getEmployee().setCity( employee.getCity() );
        employeeBean.getEmployee().setState( employee.getState() );
        employeeBean.getEmployee().setZipCode( employee.getZipCode() );

        if ( previousPage )
        {
            modelAndView = new ModelAndView( "jsp/addEmployee1.jsp", "employee", employeeBean.getEmployee() );
        }
        else
        {
            modelAndView = new ModelAndView( "jsp/addEmployee3.jsp", "employee", employeeBean.getEmployee() );
        }


        return modelAndView;
    }


    @RequestMapping(value = ADD_EMPLOYEE3_URL, method = RequestMethod.POST)
    public ModelAndView FinishAddEmployee( @ModelAttribute("employee") Employee employee,
                                           @RequestParam boolean previousPage2,
                                           HttpServletRequest request )
    {
        ModelAndView modelAndView;

        employeeBean.getEmployee().setHourlyWage( employee.getHourlyWage() );

        if ( previousPage2 )
        {
            modelAndView = new ModelAndView( "jsp/addEmployee2.jsp", "employee", employeeBean.getEmployee() );
        }
        else
        {
            List<Result> results = employeeService.addEmployee( employeeBean.getEmployee() );
            if ( new Result().resultsAreValid( results ) )
            {
                //todo setResults and go to manage employees
                employee = new Employee();
                employeeBean.setEmployee( employee );
                modelAndView = new ModelAndView( "jsp/viewEmployees.jsp" );
                modelAndView.addObject( "employee", employeeBean.getEmployee() );
                modelAndView.addObject( "results", results );
            }
            else
            {
                modelAndView = new ModelAndView( "jsp/addEmployee3.jsp", "employee", employeeBean.getEmployee() );
                modelAndView.addObject( "results", results );
            }

        }

        return modelAndView;
    }


    @RequestMapping(value = VIEW_EMPLOYEES_URL, method = RequestMethod.GET)
    public ModelAndView startViewEmployees( HttpServletRequest request )
    {
        ModelAndView modelAndView;

        try
        {
            modelAndView = new ModelAndView( "jsp/viewEmployees.jsp" );
            modelAndView.addObject( "employee", new Employee() );
        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }


        return modelAndView;
    }

    @RequestMapping(value = VIEW_EMPLOYEES_URL, method = RequestMethod.POST)
    public ModelAndView viewMembers( @ModelAttribute("employee") Employee employee, HttpServletRequest request )
    {
        ModelAndView modelAndView;

        try
        {
            modelAndView = new ModelAndView( "jsp/viewEmployees.jsp" );
            modelAndView.addObject( "employees", employeeService.getFilteredEmployees( employee ) );
        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }


        return modelAndView;
    }

    @RequestMapping(value = EDIT_EMPLOYEE_URL, method = RequestMethod.GET)
    public ModelAndView startEditEmployee( @RequestParam String employeeId, HttpServletRequest request )
    {
        ModelAndView modelAndView;

        try
        {
            Employee employee = employeeService.getEmployee( employeeId );
            modelAndView = new ModelAndView( "/jsp/editEmployee.jsp" );
            modelAndView.addObject( "employee", employee );
        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }


        return modelAndView;
    }

    @RequestMapping(value = EDIT_EMPLOYEE_URL, method = RequestMethod.POST)
    public ModelAndView editEmployee( @ModelAttribute("employee") Employee employee, HttpServletRequest request )
    {
        ModelAndView modelAndView;

        Result result;
        modelAndView = new ModelAndView( "jsp/editEmployee.jsp" );
        try
        {
            result = employeeService.updateEmployee( employee );
            modelAndView.addObject( "result", result );
        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }


        return modelAndView;
    }


    @RequestMapping(value = "/ajax/deleteEmployeeEvents", method = RequestMethod.POST)
    public
    @ResponseBody
    int deleteEmployeeEvents( @RequestParam String employeeId )
    {
        return employeeService.deleteEmployeeEvents( employeeId );
    }

    @RequestMapping(value = "/ajax/getEmployees", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Employee> getEmployees( @RequestParam String employeeFirstName )
    {
        Employee employee = new Employee();
        employee.setFirstName( employeeFirstName );
        return employeeService.getFilteredEmployees( employee );
    }

    @RequestMapping(value = "/ajax/getAllEmployees", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Employee> getAllEmployees()
    {
        Employee employee = new Employee();
        return employeeService.getFilteredEmployees( employee );
    }
}