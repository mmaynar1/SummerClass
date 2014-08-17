package com.summerclass.repository;

import com.summerclass.domain.EventStatuses;
import com.summerclass.reportdetails.EventTypeReportDetail;
import com.summerclass.reportdetails.StatusCountReportDetail;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.List;

public interface EventsDao
{
    void duplicateEventsAddingAnHour( String memberFirstName, int fromClubNumber, int toClubNumber );

    void deleteEventsAtClub( String memberFirstName, int clubNumber );

    void deleteEvents( String memberFirstName );

    void deleteEvents( String memberFirstName, int clubNumber, String eventTypeName );

    int getEventsCountByStatus( String memberId, EventStatuses status );

    void changeEventsStatuses( String memberId, EventStatuses oldStatus, EventStatuses newStatus );

    int getEventsCount( String memberFirstName );

    int getEventsCountAtClub( String memberFirstName, int clubNumber );

    void addHourToStartTime( String eventTypeName, int clubNumber );

    List<Timestamp> getStartTimes( String memberFirstName, String eventTypeName, int clubNumber );

    void createEventSession( String memberFirstName, String employeeFirstName, String statusAbcCode, int clubNumber, String eventType );

    int getEventTypeCount();

    List<StatusCountReportDetail> getStatusCountReportDetails();

    List<EventTypeReportDetail> getEventTypeReportDetails();
}
