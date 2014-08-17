package com.summerclass.controller;

import com.summerclass.domain.Company;
import com.summerclass.domain.CompanyImpl;
import com.summerclass.domain.Result;
import com.summerclass.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class EmployeeController
{
    private Logger logger = Logger.getLogger( getClass() );

    @Autowired
    private Company company;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public ModelAndView startAddEmployee()
    {
        company.setName( "EmployeeController:addEmployee" );
        return new ModelAndView( "jsp/addEmployee.jsp" );
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ModelAndView addEmployee( @RequestParam String firstName,
                                     @RequestParam String lastName,
                                     HttpSession session )
    {
        company.setId( "asdfds" );
        Company someCompany = (Company) session.getAttribute( "company" );
        Result result = employeeService.addEmployee( firstName, lastName );
        return new ModelAndView( "jsp/addEmployee.jsp", "result", result );
    }
}
