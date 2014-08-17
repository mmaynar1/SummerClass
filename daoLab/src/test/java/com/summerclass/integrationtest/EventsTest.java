package com.summerclass.integrationtest;

import com.summerclass.domain.EventStatuses;
import com.summerclass.reportdetails.EventTypeReportDetail;
import com.summerclass.reportdetails.StatusCountReportDetail;
import com.summerclass.repository.ClubDao;
import com.summerclass.repository.EventsDao;
import com.summerclass.repository.EventsDaoImpl;
import com.summerclass.repository.MemberDao;
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

    public static final String TEST_MEMBER_FIRST_NAME = "Mitchum";
    public static final String TEST_EMPLOYEE_FIRST_NAME = "Pam";
    public static final String TEST_EVENT_TYPE = "Test Event";

    @BeforeMethod
    public void beforeMethod( Method method )
    {
        eventsDao.deleteEvents( TEST_MEMBER_FIRST_NAME );
    }

    @Test
    public void testGetEventsCount()
    {
        int count = eventsDao.getEventsCount( TEST_MEMBER_FIRST_NAME );
        assertEquals( count, 0 );

        final int eventsToAdd = 10;
        final int clubNumber = 3000;
        createEventSessions( eventsToAdd, EventStatuses.pending, clubNumber );

        count = eventsDao.getEventsCount( TEST_MEMBER_FIRST_NAME );
        assertEquals( count, eventsToAdd );

        count = eventsDao.getEventsCountAtClub( TEST_MEMBER_FIRST_NAME, clubNumber );
        assertEquals( count, eventsToAdd );

        count = eventsDao.getEventsCountAtClub( TEST_MEMBER_FIRST_NAME, 3001 );
        assertEquals( count, 0 );

        count = eventsDao.getEventsCountAtClub( TEST_MEMBER_FIRST_NAME, 9999 );
        assertEquals( count, 0 );

        eventsDao.deleteEvents( TEST_MEMBER_FIRST_NAME );
        count = eventsDao.getEventsCount( TEST_MEMBER_FIRST_NAME );
        assertEquals( count, 0 );
    }

    @Test
    public void testDeleteEventsAtClub()
    {
        final int eventsToAdd = 10;
        createEventSessions( eventsToAdd, EventStatuses.pending, 3000 );
        createEventSessions( eventsToAdd, EventStatuses.complete, 3001 );

        int count = eventsDao.getEventsCount( TEST_MEMBER_FIRST_NAME );
        assertEquals( count, eventsToAdd * 2 );

        eventsDao.deleteEventsAtClub( TEST_MEMBER_FIRST_NAME, 3000 );
        count = eventsDao.getEventsCount( TEST_MEMBER_FIRST_NAME );
        assertEquals( count, eventsToAdd );

        eventsDao.deleteEventsAtClub( TEST_MEMBER_FIRST_NAME, 3001 );
        count = eventsDao.getEventsCount( TEST_MEMBER_FIRST_NAME );
        assertEquals( count, 0 );
    }


    public void createEventSessions( int eventsToAdd, EventStatuses status, int clubNumber )
    {
        for (int i = 0; i < eventsToAdd; ++i)
        {
            eventsDao.createEventSession( TEST_MEMBER_FIRST_NAME, TEST_EMPLOYEE_FIRST_NAME, status.getAbcCode(), clubNumber, TEST_EVENT_TYPE );
        }
    }

    @Test
    public void testDuplicateEventsAddingAnHour()
    {
        final int fromClubNumber = 3000;
        final int toClubNumber = 3001;

        final int eventsToAdd = 10;
        createEventSessions( eventsToAdd, EventStatuses.cancelled, fromClubNumber );
        createEventSessions( eventsToAdd, EventStatuses.complete, toClubNumber );

        int count = eventsDao.getEventsCount( TEST_MEMBER_FIRST_NAME );
        assertEquals( count, eventsToAdd * 2 );

        eventsDao.duplicateEventsAddingAnHour( TEST_MEMBER_FIRST_NAME, fromClubNumber, toClubNumber );
        count = eventsDao.getEventsCount( TEST_MEMBER_FIRST_NAME );
        assertEquals( count, eventsToAdd * 3 );

        count = eventsDao.getEventsCountAtClub( TEST_MEMBER_FIRST_NAME, fromClubNumber );
        assertEquals( count, eventsToAdd );

        count = eventsDao.getEventsCountAtClub( TEST_MEMBER_FIRST_NAME, toClubNumber );
        assertEquals( count, eventsToAdd * 2 );
    }

    @Test
    public void testAddHourToStartTime()
    {
        final int clubNumber = 3000;
        final int numberOfSessions = 10;
        createEventSessions( numberOfSessions, EventStatuses.pending, clubNumber );

        List<Timestamp> originalStartTimes = eventsDao.getStartTimes( TEST_MEMBER_FIRST_NAME, TEST_EVENT_TYPE, clubNumber );
        assertEquals( numberOfSessions, originalStartTimes.size() );

        eventsDao.addHourToStartTime( TEST_EVENT_TYPE, clubNumber );

        List<Timestamp> modifiedStartTimes = eventsDao.getStartTimes( TEST_MEMBER_FIRST_NAME, TEST_EVENT_TYPE, clubNumber );
        assertEquals( numberOfSessions, modifiedStartTimes.size() );

        for (int i = 0; i < numberOfSessions; i++)
        {
            assertTrue( originalStartTimes.get( i ).compareTo( modifiedStartTimes.get( i ) ) < 0 );
        }
    }

    @Test
    public void getStatusCountReportDetails()
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
        createEventSessions( numberOfEvents, EventStatuses.pending, clubNumber );
        createEventSessions( numberOfEvents, EventStatuses.complete, clubNumber );
        createEventSessions( numberOfEvents, EventStatuses.cancelled, clubNumber );

        String id = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );

        verifyEventsCountStatuses( id, numberOfEvents );

        eventsDao.deleteEvents( TEST_MEMBER_FIRST_NAME );

        verifyEventsCountStatuses( id, 0 );
    }

    private void verifyEventsCountStatuses( String id, int expected )
    {
        int count = eventsDao.getEventsCountByStatus( id, EventStatuses.pending );
        assertEquals( count, expected );

        count = eventsDao.getEventsCountByStatus( id, EventStatuses.complete );
        assertEquals( count, expected );

        count = eventsDao.getEventsCountByStatus( id, EventStatuses.cancelled );
        assertEquals( count, expected );
    }

    private void verifyEventsCountStatuses( String id, int pendingCount, int completeCount, int cancelledCount )
    {
        int count = eventsDao.getEventsCountByStatus( id, EventStatuses.pending );
        assertEquals( count, pendingCount );

        count = eventsDao.getEventsCountByStatus( id, EventStatuses.complete );
        assertEquals( count, completeCount );

        count = eventsDao.getEventsCountByStatus( id, EventStatuses.cancelled );
        assertEquals( count, cancelledCount );
    }

    @Test
    public void testChangeEventsStatuses()
    {
        final int numberOfEvents = 10;
        int clubNumber = 3000;
        createEventSessions( numberOfEvents, EventStatuses.pending, clubNumber );
        createEventSessions( numberOfEvents, EventStatuses.complete, clubNumber );
        createEventSessions( numberOfEvents, EventStatuses.cancelled, clubNumber );

        String id = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );

        verifyEventsCountStatuses( id, numberOfEvents );

        eventsDao.changeEventsStatuses( id, EventStatuses.pending, EventStatuses.complete );
        verifyEventsCountStatuses( id, 0, numberOfEvents * 2, numberOfEvents );

        eventsDao.changeEventsStatuses( id, EventStatuses.cancelled, EventStatuses.complete );
        verifyEventsCountStatuses( id, 0, numberOfEvents * 3, 0 );
    }
}