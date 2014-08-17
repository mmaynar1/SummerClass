package com.summerclass.repository;

import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventStatuses;
import com.summerclass.reportdetails.EventTypeAndStatusReportDetail;
import com.summerclass.reportdetails.EventTypeReportDetail;
import com.summerclass.reportdetails.StatusCountReportDetail;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;

public interface EventsDao
{
    void duplicateEventsAddingAnHour( String memberId, String fromClubId, String toClubId );

    void deleteEventsAtClub( String memberId, String clubId );

    void deleteEvents( String memberId );

    void deleteEvents( String memberId, String clubId, String eventTypeId );

    int getEventsCountByStatus( String memberId, EventStatuses status );

    int getEventsCount();

    int getEventsCountByStatus( EventStatuses status );

    void changeEventsStatuses( String memberId, EventStatuses oldStatus, EventStatuses newStatus );

    int getEventsCount( String memberId );

    void createRandomEventSessions( int limit );

    int getEventsCountAtClub( String memberId, String clubId );

    void addHourToStartTime( String eventTypeId, String clubId );

    List<Timestamp> getStartTimes( String memberId, String eventTypeId, String clubId );

    void createEventSession( EventSession eventSession );

    String getEventTypeId( String name );

    int getEventTypeCount();

    List<StatusCountReportDetail> getStatusCountReportDetails();

    EventTypeReportDetail getOneEventTypeReportDetails();

    List<EventTypeReportDetail> getEventTypeReportDetails();

    List<EventTypeAndStatusReportDetail> getEventTypeAndStatusReportDetails();

    int getEventsCountByMemberClubAndEventType( String memberId, String clubId, String eventTypeId );
}
