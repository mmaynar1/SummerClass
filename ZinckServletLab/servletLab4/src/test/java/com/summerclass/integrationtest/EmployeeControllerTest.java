package com.summerclass.integrationtest;

import com.summerclass.domain.Employee;
import com.summerclass.repository.*;
import com.summerclass.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EmployeeControllerTest extends SpringIntegrationTest
{
    public static final String TEST_MEMBER_FIRST_NAME = "Kenneth";
    public static final String TEST_MEMBER_LAST_NAME = "Teeter";
    public static final String TEST_EMPLOYEE_FIRST_NAME = "Andy";
    public static final String TEST_EMPLOYEE_LAST_NAME = "Smith";

    @Autowired
   private EmployeeDao employeeDao;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private ClubDao clubDao;

    @Autowired
    private ControllerDao controllerDao;

    @Autowired
    private EventTypeDao eventTypeDao;

    @Test
    public void testGetEmployees() throws Exception
    {
        String employeeFirstName =  TEST_EMPLOYEE_FIRST_NAME;
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "ajax", "1" );
        parameters.put( "employeeFirstName", employeeFirstName );

        Object body = null;
        String url = "/ajax/getEmployees.spr";

        List<Employee> employees = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, List.class );
        for(Employee employee : employees)
        {
            assertEquals( TEST_EMPLOYEE_FIRST_NAME, employee.getFirstName() );
        }
    }

    @Test
        public void testDeleteEmployeeEvents() throws Exception
        {
            String employeeId = employeeDao.getEmployeeId( TEST_EMPLOYEE_FIRST_NAME, TEST_EMPLOYEE_LAST_NAME );
            Map<String, String> parameters = new HashMap<>();
            parameters.put( "ajax", "1" );
            parameters.put( "employeeId", employeeId );

            Object body = null;
            String url = "/ajax/deleteEmployeeEvents.spr";

            eventDao.createEvent( memberDao.getMemberId( TEST_MEMBER_FIRST_NAME, TEST_MEMBER_LAST_NAME ),
                                  employeeId,
                                  clubDao.getClubId( 3000 ),
                                  eventTypeDao.getEventTypeId( "Kick Boxing" ));
            int count = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, Integer.class );
            assertTrue( count > 0 );
            employeeService.deleteEmployeeEvents( employeeId );
            count = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, Integer.class );
            assertEquals( count, 0 );
        }
}
