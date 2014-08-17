package com.summerclass.integrationtest;

import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventStatuses;
import com.summerclass.reportdetails.EventTypeAndStatusReportDetail;
import com.summerclass.reportdetails.EventTypeReportDetail;
import com.summerclass.reportdetails.StatusCountReportDetail;
import com.summerclass.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

public class EventsTest extends SpringIntegrationTest
{
    @Autowired
    private EventsDao eventsDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ClubDao clubDao;
    @Autowired
    private EmployeeDao employeeDao;

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
    public void testGetEventsCountByMemberId()
    {
        final String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        int count = eventsDao.getEventsCount( memberId );
        assertEquals( count, 0 );

        final int eventsToAdd = 10;
        final int clubNumber = 3000;
        final String clubId = clubDao.getClubId( clubNumber );
        createEventSessions( eventsToAdd, EventStatuses.pending, clubId );

        count = eventsDao.getEventsCount( memberId );
        assertEquals( count, eventsToAdd );

        count = eventsDao.getEventsCountAtClub( memberId, clubId );
        assertEquals( count, eventsToAdd );

        count = eventsDao.getEventsCountAtClub( memberId, "111111111111111111111111111" );
        assertEquals( count, 0 );

        eventsDao.deleteEvents( memberId );
        count = eventsDao.getEventsCount( memberId );
        assertEquals( count, 0 );
    }

    @Test
    public void testDeleteEventsByMemberClubAndEventType()
    {
        int eventsToAdd = 10;
        String clubId = clubDao.getClubId( 3000 );
        createEventSessions( eventsToAdd, EventStatuses.pending, clubId );

        final String testMemberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        final String testEventTypeId = eventsDao.getEventTypeId( TEST_EVENT_TYPE );
        int count = eventsDao.getEventsCountByMemberClubAndEventType(testMemberId, clubId, testEventTypeId);
        assertTrue( count >= eventsToAdd );
        eventsDao.deleteEvents(testMemberId, clubId, testEventTypeId);
        count = eventsDao.getEventsCountByMemberClubAndEventType(testMemberId, clubId, testEventTypeId);
        assertEquals( count, 0 );
    }

    @Test
    public void testDeleteEventsAtClub()
    {
        final int eventsToAdd = 10;
        createEventSessions( eventsToAdd, EventStatuses.pending, clubDao.getClubId( 3000 ) );
        createEventSessions( eventsToAdd, EventStatuses.complete, clubDao.getClubId( 3001 ) );

        final String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        int count = eventsDao.getEventsCount( memberId );
        assertEquals( count, eventsToAdd * 2 );

        eventsDao.deleteEventsAtClub( memberId, clubDao.getClubId( 3000 ) );
        count = eventsDao.getEventsCount( memberId );
        assertEquals( count, eventsToAdd );

        eventsDao.deleteEventsAtClub( memberId, clubDao.getClubId( 3001 ) );
        count = eventsDao.getEventsCount( memberId );
        assertEquals( count, 0 );
    }


    public void createEventSessions( int eventsToAdd, EventStatuses status, String clubId )
    {
        final String testMemberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        final String testEmployeeId = employeeDao.getEmployeeId( TEST_EMPLOYEE_FIRST_NAME, TEST_EMPLOYEE_LAST_NAME );
        final String testEventTypeId = eventsDao.getEventTypeId( TEST_EVENT_TYPE );

        EventSession eventSession = new EventSession( testMemberId,
                                                      testEmployeeId,
                                                      status.getAbcCode(),
                                                      testEventTypeId,
                                                      clubId );
        for (int i = 0; i < eventsToAdd; ++i)
        {
            eventsDao.createEventSession( eventSession );
        }
    }

    @Test
    public void testGetEventsCount()
    {
        assertTrue( eventsDao.getEventsCount() > 0 );
    }

    @Test
    public void testCreateRandomEventSessions()
    {
        final int beforeEventCount = eventsDao.getEventsCount();

        final int numberAdded = 10;
        eventsDao.createRandomEventSessions( numberAdded );

        assertEquals( eventsDao.getEventsCount(), beforeEventCount + numberAdded );

    }

    @Test
    public void testDuplicateEventsAddingAnHour()
    {
        final int fromClubNumber = 3000;
        final String fromClubId = clubDao.getClubId( fromClubNumber );
        final int toClubNumber = 3001;
        final String toClubId = clubDao.getClubId( toClubNumber );

        final int eventsToAdd = 10;
        createEventSessions( eventsToAdd, EventStatuses.cancelled, fromClubId );
        createEventSessions( eventsToAdd, EventStatuses.complete, toClubId );

        final String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        int count = eventsDao.getEventsCount( memberId );
        assertEquals( count, eventsToAdd * 2 );

        eventsDao.duplicateEventsAddingAnHour( memberId, fromClubId, toClubId );
        count = eventsDao.getEventsCount( memberId );
        assertEquals( count, eventsToAdd * 3 );

        count = eventsDao.getEventsCountAtClub( memberId, fromClubId );
        assertEquals( count, eventsToAdd );

        count = eventsDao.getEventsCountAtClub( memberId, toClubId );
        assertEquals( count, eventsToAdd * 2 );
    }

    @Test
    public void testAddHourToStartTime()
    {
        final int clubNumber = 3000;
        final String clubId = clubDao.getClubId( clubNumber );
        final String eventTypeId = eventsDao.getEventTypeId( TEST_EVENT_TYPE );
        final String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        final int numberOfSessions = 10;
        createEventSessions( numberOfSessions, EventStatuses.pending, clubDao.getClubId( clubNumber ) );

        List<Timestamp> originalStartTimes = eventsDao.getStartTimes( memberId, eventTypeId, clubId );
        assertEquals( numberOfSessions, originalStartTimes.size() );

        eventsDao.addHourToStartTime( eventTypeId, clubId );

        List<Timestamp> modifiedStartTimes = eventsDao.getStartTimes( memberId, eventTypeId, clubId );
        assertEquals( numberOfSessions, modifiedStartTimes.size() );

        for (int i = 0; i < numberOfSessions; i++)
        {
            assertTrue( originalStartTimes.get( i ).compareTo( modifiedStartTimes.get( i ) ) < 0 );
        }
    }

    @Test
    public void testGetStatusCountReportDetails()
    {
        List<StatusCountReportDetail> details = eventsDao.getStatusCountReportDetails();
        int size = details.size();

        assertEquals( size, EventStatuses.values().length + 1 );

        StatusCountReportDetail lastDetail = details.get( size - 1 );
        assertTrue( lastDetail.getStatusName().equals( EventsDaoImpl.TOTAL_COLUMN_LABEL ) );

        int countTotal = 0;
        for (int i = 0; i < size - 1; i++)
        {
            countTotal += details.get( i ).getCount();
        }

        assertEquals( countTotal, lastDetail.getCount() );
    }

    @Test
    public void testGetEventTypeAndStatusReportDetails()
    {
        List<EventTypeAndStatusReportDetail> details = eventsDao.getEventTypeAndStatusReportDetails();
        int size = details.size();
        assertEquals( size, clubDao.getClubCount() * eventsDao.getEventTypeCount() * EventStatuses.values().length );
    }

    @Test
    public void testGetEventTypeCount()
    {
        assertTrue( eventsDao.getEventTypeCount() > 0 );
    }

    @Test
    public void testGetEventTypeReportDetails()
    {
        List<EventTypeReportDetail> details = eventsDao.getEventTypeReportDetails();
        int size = details.size();
        assertEquals( size, clubDao.getClubCount() * eventsDao.getEventTypeCount() );
    }

    @Test
    public void testGetEventsCountByStatus()
    {
        final int numberOfEvents = 10;
        int clubNumber = 3000;
        String clubId = clubDao.getClubId( clubNumber );
        createEventSessions( numberOfEvents, EventStatuses.pending, clubId );
        createEventSessions( numberOfEvents, EventStatuses.complete, clubId );
        createEventSessions( numberOfEvents, EventStatuses.cancelled, clubId );

        String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );

        verifyEventsCountStatuses( memberId, numberOfEvents );

        eventsDao.deleteEvents( memberId );

        verifyEventsCountStatuses( memberId, 0 );
    }

    private void verifyEventsCountStatuses( String memberId, int expected )
    {
        int count = eventsDao.getEventsCountByStatus( memberId, EventStatuses.pending );
        assertEquals( count, expected );

        count = eventsDao.getEventsCountByStatus( memberId, EventStatuses.complete );
        assertEquals( count, expected );

        count = eventsDao.getEventsCountByStatus( memberId, EventStatuses.cancelled );
        assertEquals( count, expected );
    }

    private void verifyEventsCountStatuses( String memberId, int pendingCount, int completeCount, int cancelledCount )
    {
        int count = eventsDao.getEventsCountByStatus( memberId, EventStatuses.pending );
        assertEquals( count, pendingCount );

        count = eventsDao.getEventsCountByStatus( memberId, EventStatuses.complete );
        assertEquals( count, completeCount );

        count = eventsDao.getEventsCountByStatus( memberId, EventStatuses.cancelled );
        assertEquals( count, cancelledCount );
    }

    @Test
    public void testChangeEventsStatuses()
    {
        final int numberOfEvents = 10;
        int clubNumber = 3000;
        String clubId = clubDao.getClubId( clubNumber );
        createEventSessions( numberOfEvents, EventStatuses.pending, clubId );
        createEventSessions( numberOfEvents, EventStatuses.complete, clubId );
        createEventSessions( numberOfEvents, EventStatuses.cancelled, clubId );

        String id = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );

        verifyEventsCountStatuses( id, numberOfEvents );

        eventsDao.changeEventsStatuses( id, EventStatuses.pending, EventStatuses.complete );
        verifyEventsCountStatuses( id, 0, numberOfEvents * 2, numberOfEvents );

        eventsDao.changeEventsStatuses( id, EventStatuses.cancelled, EventStatuses.complete );
        verifyEventsCountStatuses( id, 0, numberOfEvents * 3, 0 );
    }
}