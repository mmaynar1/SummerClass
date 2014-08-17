package com.summerclass.repository;

import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventStatus;
import com.summerclass.domain.EventType;

import java.sql.Timestamp;
import java.util.List;

public interface EventDao
{
    List<EventSession> getEvents( String clubId, String employeeId );

    List<EventSession> getAllEvents();

    String getRandomEventId();

    String getRandomEventId( EventStatus status );

    String getStatusAbcCode( String statusId );

    EventSession getEvent( String eventId );

    int deleteEmployeeEvents( String clubId, String employeeId );

    void setStatus( String eventId, EventStatus status );

    void createEvents( String clubId, String employeeId, int eventToCreate );

    int createEvent( String memberId, String employeeId, String clubId, String eventTypeId);

    List<String> getRandomEventIds( EventStatus status, int count );

    int getEventCount();

    int deleteEventSessions( List<String> eventSessionIds );

    List<EventType> getEventTypes();

    List<EventSession> getEvents(String member, String employee, String club, String eventType, String status);

    List<EventSession> getMemberEvents( String memberId );

    List<EventSession> getPendingMemberEvents( String memberId );

    int editEvent( String eventId, String employeeId, Timestamp startTime );
}
