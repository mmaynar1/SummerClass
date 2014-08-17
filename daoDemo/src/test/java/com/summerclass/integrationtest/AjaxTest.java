package com.summerclass.integrationtest;

import com.summerclass.domain.Employee;
import com.summerclass.domain.IdName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class AjaxTest extends SpringIntegrationTest
{
    @Autowired
    private ControllerDao controllerDao;

    @Test
    public void testLogin() throws Exception
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "ajax", "1" );
        parameters.put( "userName", "user1" );
        parameters.put( "password", "asdf1234" );

        Object body = null;
        String url = "/ajax/login.spr";
        boolean worked = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, Boolean.class );
        assertTrue( worked );
    }

    @Test
    public void testCountCharacters() throws Exception
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "ajax", "1" );

        Object body = new IdName( "42", "life ..." );
        String url = "/ajax/countCharacters.spr";
        String count = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, String.class );
        assertNotNull( count );
        assertEquals( count, "10" );
    }

    @Test
    public void testGetEmployees() throws Exception
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "ajax", "1" );

        Object body = null;
        String url = "/ajax/getEmployees.spr";
        List<Employee> employees = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, List.class );
        assertNotNull( employees );
        assertTrue( employees.size() > 0 );
        assertNotNull( employees.get( 0 ).getFirstName() );
    }

    @Test
    public void testMeaningOfLife() throws Exception
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "ajax", "1" );

        Object body = null;
        String url = "/ajax/meaningOfLife.spr";
        IdName idName = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, IdName.class );
        assertEquals( idName.getId(), "42" );
    }

    @Test
    public void testReverse() throws Exception
    {
        Map<String, String> parameters = new HashMap<>();
        String source = "abcd";
        parameters.put( "source", source );
        parameters.put( "ajax", "1" );
        Object body = null;
        String url = "/ajax/reverseString.spr";
        String result = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, String.class );
        assertEquals( result, new StringBuilder( source ).reverse().toString() );
    }

}
