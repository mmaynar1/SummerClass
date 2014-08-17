package com.summerclass.controller;

import com.summerclass.domain.Employee;
import com.summerclass.domain.IdName;
import com.summerclass.domain.LoggedInMember;
import com.summerclass.service.EmployeeService;
import com.summerclass.utility.StringSupport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AjaxController
{
    // todo see changes in SpringConfiguration.java

    private static final Logger logger = Logger.getLogger( AjaxController.class );

    @Autowired
    private LoggedInMember loggedInMember;

    @Autowired
    private EmployeeService employeeService;

    // todo string in/string out
    @RequestMapping(value = "/ajax/reverseString", method = RequestMethod.POST)
    public
    @ResponseBody
    String reverseString( @RequestParam String source )
    {
        return new StringBuilder( source ).reverse().toString();
    }

    // todo nothing in/pojo out
    @RequestMapping(value = "/ajax/meaningOfLife", method = RequestMethod.POST)
    public
    @ResponseBody
    IdName meaningOfLife()
    {
        return new IdName( "42", "Answer to the Ultimate Question of Life, the Universe, and Everything" );
    }

    // todo pojo in/string out
    @RequestMapping(value = "/ajax/countCharacters", method = RequestMethod.POST)
    public
    @ResponseBody
    String countCharacters( @RequestBody IdName idName )
    {
        return (idName.getId().length() + idName.getName().length()) + "";
    }

    // todo strings in, boolean out
    @RequestMapping(value = "/ajax/login", method = RequestMethod.POST)
    public
    @ResponseBody
    boolean login( @RequestParam String userName, @RequestParam String password )
    {
        boolean worked = (StringSupport.safeEqual( userName, "user1" ) &&
                          StringSupport.safeEqual( password, "asdf1234" ));

        if ( worked )
        {
            loggedInMember.setUserName( userName );
        }
        else
        {
            loggedInMember.clear();
        }

        return worked;
    }

    // todo nothing in/list pojo out
    @RequestMapping(value = "/ajax/getEmployees", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Employee> getEmployees()
    {
        return employeeService.getAll();
    }

    @ExceptionHandler
    public HttpEntity handleException( Exception exception )
    {
        logger.error( exception );
        return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );
    }
}
