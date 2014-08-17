package com.summerclass.integrationtest;

import com.summerclass.domain.Selectable;
import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventStatus;
import com.summerclass.repository.ClubDao;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.repository.EventDao;
import com.summerclass.utility.RandomGenerator;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class EventTest extends SpringIntegrationTest
{
    @Autowired
    private EventDao eventDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ClubDao clubDao;

    @Test
    public void testGetAll()
    {
        List<EventSession> eventSessions = eventDao.getAllEvents();
        validateEvents( eventSessions );
    }

    private void validateEvent( EventSession eventSession )
    {
        assertNotNull( eventSession );

        assertTrue( StringSupport.isGuid( eventSession.getEventId() ) );
        assertNotNull( eventSession.getStartDateTime() );

        validateIdName( eventSession.getClub() );
        validateIdName( eventSession.getEmployee() );
        validateIdName( eventSession.getMember() );
        validateIdName( eventSession.getEventType() );
        validateIdName( eventSession.getStatus() );
    }

    private void validateIdName( Selectable selectable )
    {
        assertNotNull( selectable );
        assertTrue( StringSupport.isGuid( selectable.getId() ) );
        assertNotNull( selectable.getName() );
    }

    @Test
    public void testGetRandomEventId()
    {
        String eventId = eventDao.getRandomEventId();
        assertTrue( StringSupport.isGuid( eventId ) );
    }

    @Test
    public void testGetEvent()
    {
        String eventId = eventDao.getRandomEventId();
        assertTrue( StringSupport.isGuid( eventId ) );

        EventSession eventSession = eventDao.getEvent( eventId );
        validateEvent( eventSession );
    }

    @Test
    public void testGetRandomEventByStatus()
    {
        String eventId = eventDao.getRandomEventId( EventStatus.pending );
        assertTrue( StringSupport.isGuid( eventId ) );

        EventSession eventSession = eventDao.getEvent( eventId );
        validateEvent( eventSession );

        String statusAbcCode = eventDao.getStatusAbcCode( eventSession.getStatus().getId() );
        assertEquals( statusAbcCode, EventStatus.pending.getAbcCode() );
    }

    @Test
    public void testUpdateStatus()
    {
        EventStatus oldStatus = EventStatus.pending;
        EventStatus newStatus = EventStatus.complete;

        String eventId = eventDao.getRandomEventId( oldStatus );
        assertTrue( StringSupport.isGuid( eventId ) );

        EventSession eventSession = eventDao.getEvent( eventId );
        String statusAbcCode = eventDao.getStatusAbcCode( eventSession.getStatus().getId() );
        assertEquals( statusAbcCode, oldStatus.getAbcCode() );

        eventDao.setStatus( eventId, newStatus );

        eventSession = eventDao.getEvent( eventId );
        validateEvent( eventSession );
        statusAbcCode = eventDao.getStatusAbcCode( eventSession.getStatus().getId() );
        assertEquals( statusAbcCode, newStatus.getAbcCode() );
    }

    @Test
    public void testCreateEvents()
    {
        createEvents( 3000, "Dan" );
    }

    public int createEvents( int clubNumber, String employeeFirstName )
    {
        String employeeId = getOrCreateEmployeeId( employeeFirstName );

        assertTrue( StringSupport.isGuid( employeeId ) );

        String clubId = clubDao.getClubId( clubNumber );

        final int eventsToCreate = 5;
        eventDao.createEvents( clubId, employeeId, eventsToCreate );
        return eventsToCreate;
    }

    private String getOrCreateEmployeeId( String employeeFirstName )
    {
        String employeeId;
        List<String> employeeIds = employeeDao.getEmployeeIds( employeeFirstName );

        if ( employeeIds.size() == 0 )
        {
            employeeId = employeeDao.createEmployee( employeeFirstName, RandomGenerator.getName() );
        }
        else
        {
            employeeId = employeeIds.get( 0 );
        }

        return employeeId;
    }

    private String getEmployeeId( String firstName )
    {
        String employeeId = getOrCreateEmployeeId( firstName );
        assertTrue( StringSupport.isGuid( employeeId ) );
        return employeeId;
    }

    @Test
    public void testDeleteEmployeeEvents()
    {
        String firstName = "Ellen";
        String employeeId = getEmployeeId( firstName );
        final int clubNumber = 3000;
        String clubId = clubDao.getClubId( clubNumber );

        // make eventSessions in case there are none
        int eventsCreated = createEvents( clubNumber, firstName );

        // verify that there are eventSessions
        List<EventSession> eventSessions = eventDao.getEvents( clubId, employeeId );
        validateEvents( eventSessions );
        assertTrue( eventSessions.size() >= eventsCreated );

        // delete the eventSessions
        int eventsDeleted = eventDao.deleteEmployeeEvents( clubId, employeeId );
        assertEquals( eventsDeleted, eventSessions.size() );

        // verify that there are no eventSessions
        eventSessions = eventDao.getEvents( clubId, employeeId );
        assertNotNull( eventSessions );
        assertTrue( eventSessions.size() == 0 );
    }

    private void validateEvents( List<EventSession> eventSessions )
    {
        assertNotNull( eventSessions );

        assertTrue( eventSessions.size() > 0 );

        for (EventSession eventSession : eventSessions)
        {
            validateEvent( eventSession );
        }
    }

    @Test
    public void testDeleteEvents()
    {
        final int deleteCount = 5;
        String employeeId = getEmployeeId( "Ellen" );
        final int clubNumber = 3000;
        String clubId = clubDao.getClubId( clubNumber );
        eventDao.createEvents( clubId, employeeId, EventStatus.pending, deleteCount );

        List<String> eventSessionIds = eventDao.getRandomEventIds( EventStatus.pending, deleteCount );
        assertTrue( eventSessionIds.size() == deleteCount );

        int countBeforeDelete = eventDao.getEventCount();

        int eventSessionsDeleted = eventDao.deleteEventSessions( eventSessionIds );
        assertTrue( eventSessionsDeleted == deleteCount );

        int countAfterDelete = eventDao.getEventCount();
        assertTrue( countAfterDelete == (countBeforeDelete - deleteCount) );
    }
}
