package com.summerclass.integrationtest;

import com.summerclass.domain.Employee;
import com.summerclass.repository.*;
import com.summerclass.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class EventControllerTest extends SpringIntegrationTest
{
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ControllerDao controllerDao;

    @Autowired
    private ClubDao clubDao;

    @Autowired
    private EventTypeDao eventTypeDao;

    @Autowired
    private EventDao eventDao;

    public static final String TEST_MEMBER_FIRST_NAME = "Kenneth";
    public static final String TEST_MEMBER_LAST_NAME = "Teeter";
    public static final String TEST_EMPLOYEE_FIRST_NAME = "Andy";
    public static final String TEST_EMPLOYEE_LAST_NAME = "Smith";

    @Test
    public void testCreateEvent() throws Exception
    {
        String employeeId = employeeDao.getEmployeeId( TEST_EMPLOYEE_FIRST_NAME, TEST_EMPLOYEE_LAST_NAME );
        String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME, TEST_MEMBER_LAST_NAME );
        String clubId = clubDao.getClubId( 3000 );
        String eventTypeId = eventTypeDao.getEventTypeId( "Kick Boxing" );
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "ajax", "1" );
        parameters.put("employee", employeeId );
        parameters.put( "memberId", memberId  );
        parameters.put( "club", clubId  );
        parameters.put( "eventType", eventTypeId  );

        Object body = null;
        String url = "/ajax/createEvent.spr";

        int countBefore = eventDao.getEventCount();
       int rowsAdded = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, Integer.class );
        assertEquals( rowsAdded, 1 );
        int countAfter =  eventDao.getEventCount();
        assertEquals( countBefore + 1, countAfter );


    }
}
