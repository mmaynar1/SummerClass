package com.summerclass.integrationtest;

import com.summerclass.domain.*;
import com.summerclass.repository.ClubDao;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.repository.EventDao;
import com.summerclass.repository.MemberDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class EventTest extends SpringIntegrationTest
{
    @Autowired
    private EventDao eventDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ClubDao clubDao;

    public static final String TEST_MEMBER_FIRST_NAME = "Opie";
    public static final String TEST_MEMBER_LAST_NAME = "McFarlan";
    public static final String TEST_EMPLOYEE_FIRST_NAME = "Dan";
    public static final String TEST_EMPLOYEE_LAST_NAME = "Small";
    public static final String TEST_EVENT_TYPE = "Pilates";
    public static final String TEST_CLUB_NAME = "Sherwood";

    @Test
    public void testGetEventTypes()
    {
        List<EventType> eventTypes = eventDao.getEventTypes();
        assertEquals( eventTypes.size(), eventDao.getEventTypeCount() );
        for (EventType eventType : eventTypes)
        {
            assertTrue( StringSupport.isGuid( eventType.getEventTypeId() ) );
        }
    }

    @Test
    public void getEventSessions()
    {
        List<EventSession> eventSessions = eventDao.getEventSessions();
        assertNotNull( eventSessions );
        for (EventSession eventSession : eventSessions)
        {
            validateEventSession( eventSession );
        }
    }

    private void validateEventSession( EventSession eventSession )
    {
        assertTrue( StringSupport.isGuid( eventSession.getMember().getId() ) );
        assertTrue( StringSupport.isGuid( eventSession.getEmployee().getId() ) );
        assertTrue( StringSupport.isGuid( eventSession.getStatus().getId() ) );
        assertTrue( StringSupport.isGuid( eventSession.getEventType().getId() ) );
        assertTrue( StringSupport.isGuid( eventSession.getClub().getId() ) );
        assertTrue( StringSupport.isGuid( eventSession.getEventId() ) );
    }

    @Test
    public void getFilteredEventSessions()
    {
        String memberName = TEST_MEMBER_FIRST_NAME + " " + TEST_MEMBER_LAST_NAME;
        SearchFilters filters = new SearchFilters( memberName, "", "", "", "" );
        List<EventSession> eventSessions = eventDao.getEventSessions( filters );
        for (EventSession eventSession : eventSessions)
        {
            validateEventSession( eventSession );
            assertTrue( eventSession.getMember().getName().equals( memberName ) );
        }

        filters.setMemberName( "" );
        eventSessions = eventDao.getEventSessions( filters );
        for (EventSession eventSession : eventSessions)
        {
            validateEventSession( eventSession );
        }

        filters = new SearchFilters( null, null, null, null, null );
        eventSessions = eventDao.getEventSessions( filters );
        for (EventSession eventSession : eventSessions)
        {
            validateEventSession( eventSession );
        }

        filters.setClub( TEST_CLUB_NAME );
        eventSessions = eventDao.getEventSessions( filters );
        for (EventSession eventSession : eventSessions)
        {
            validateEventSession( eventSession );
            assertTrue( eventSession.getClub().getName().equals( TEST_CLUB_NAME ) );
        }

        filters.setStatus( EventStatus.pending.getCaption() );
        eventSessions = eventDao.getEventSessions( filters );
        for (EventSession eventSession : eventSessions)
        {
            validateEventSession( eventSession );
            assertTrue( eventSession.getClub().getName().equals( TEST_CLUB_NAME ) );
            assertTrue( eventSession.getStatus().getName().equals( EventStatus.pending.getCaption() ) );
        }

    }

    @Test
    public void testCreateEventSession()
    {
        final String testMemberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME, TEST_MEMBER_LAST_NAME );
        final String testEmployeeId = employeeDao.getEmployeeId( TEST_EMPLOYEE_FIRST_NAME, TEST_EMPLOYEE_LAST_NAME );
        final String testEventTypeId = eventDao.getEventTypeId( TEST_EVENT_TYPE );
        final String testClubId = clubDao.getClubId( 3000 );

        EventSession eventSession = new EventSession();
        eventSession.setMember( new IdName( testMemberId, "" ) );
        eventSession.setEmployee( new IdName( testEmployeeId, "" ) );
        eventSession.setStatus( new IdName( EventStatus.pending.getAbcCode(), EventStatus.pending.getCaption() ) );
        eventSession.setEventType( new IdName( testEventTypeId, "" ) );
        eventSession.setClub( new IdName( testClubId, "" ) );

        int countBefore = eventDao.getEventSessionCount();
        eventDao.createEventSession( eventSession );
        int countAfter = eventDao.getEventSessionCount();
        assertEquals( countBefore, countAfter - 1 );
    }
}