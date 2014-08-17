package com.summerclass.integrationtest;

import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventStatuses;
import com.summerclass.repository.ClubDao;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.repository.EventsDao;
import com.summerclass.repository.MemberDao;
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
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ClubDao clubDao;

    public static final String TEST_MEMBER_FIRST_NAME = "Mitchum";
    public static final String TEST_EMPLOYEE_FIRST_NAME = "Pam";
    public static final String TEST_EMPLOYEE_LAST_NAME = "Beasley";
    public static final String TEST_EVENT_TYPE = "Test Event";

    @BeforeMethod
    public void beforeMethod( Method method )
    {
        String testMemberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        eventsDao.deleteEvents( testMemberId );
    }

    @Test
    public void testGetEmployees()
    {

        final String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        final String employeeId = employeeDao.getEmployeeId( TEST_EMPLOYEE_FIRST_NAME, TEST_EMPLOYEE_LAST_NAME );
        final String eventTypeId = eventsDao.getEventTypeId( TEST_EVENT_TYPE );
        final String clubId = clubDao.getClubId( 3000 );

        EventSession eventSession = new EventSession( memberId,
                                                      employeeId,
                                                      EventStatuses.pending.getAbcCode(),
                                                      eventTypeId,
                                                      clubId );

        eventsDao.createEventSession( eventSession );

        List<String> employees = employeeDao.getEmployeeNames( eventTypeId, clubId );
        assertTrue( employees.size() > 0 );
        assertTrue( employees.contains( TEST_EMPLOYEE_FIRST_NAME + " " + TEST_EMPLOYEE_LAST_NAME ) );
    }

    @Test
    public void testDeleteEvents()
    {
        final String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        final String employeeId = employeeDao.getEmployeeId( TEST_EMPLOYEE_FIRST_NAME, TEST_EMPLOYEE_LAST_NAME );
        final String eventTypeId = eventsDao.getEventTypeId( TEST_EVENT_TYPE );
        final String clubId = clubDao.getClubId( 3000 );

        EventSession eventSession = new EventSession( memberId,
                                                      employeeId,
                                                      EventStatuses.pending.getAbcCode(),
                                                      eventTypeId,
                                                      clubId );

        eventsDao.createEventSession( eventSession );
        assertTrue( employeeDao.getEventsCount( employeeId ) > 0 );
        employeeDao.deleteEvents( employeeId );
        assertEquals( employeeDao.getEventsCount( employeeId ), 0 );

    }
}