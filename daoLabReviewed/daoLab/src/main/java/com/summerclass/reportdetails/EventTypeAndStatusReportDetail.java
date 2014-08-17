package com.summerclass.reportdetails;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventTypeAndStatusReportDetail
{
    private final String clubName;
    private final String eventTypeName;
    private final String statusName;
    private final int count;

    public EventTypeAndStatusReportDetail()
    {
        this( null, null, null, 0 );
    }

    public EventTypeAndStatusReportDetail( String clubName, String eventTypeName, String statusName, int count )
    {
        this.clubName = clubName;
        this.eventTypeName = eventTypeName;
        this.count = count;
        this.statusName = statusName;
    }

    public static class Mapper implements RowMapper<EventTypeAndStatusReportDetail>
    {
        public static final String QUERY = "select c.c_number clubNumber,\n" +
                                           "et.et_name eventType,\n" +
                                           "s.s_name eventStatus,\n" +
                                           "(select count(*)\n" +
                                           "from eventSessions sub_es\n" +
                                           "where sub_es.c_id = c.c_id\n" +
                                           "and sub_es.et_id = et.et_id\n" +
                                           "and sub_es.s_id = s.s_id\n" +
                                           ") counts";

        public EventTypeAndStatusReportDetail mapRow( ResultSet resultSet, int rowNum ) throws SQLException
        {
            return getEventTypeAndStatusReportDetail( resultSet );
        }

    }

    private static EventTypeAndStatusReportDetail getEventTypeAndStatusReportDetail( ResultSet resultSet ) throws SQLException
    {
        String clubName = resultSet.getString( "clubNumber" );
        String eventTypeName = resultSet.getString( "eventType" );
        String status = resultSet.getString( "eventStatus" );
        int count = resultSet.getInt( "counts" );

        return new EventTypeAndStatusReportDetail( clubName, eventTypeName, status, count );
    }



    public String getClubName()
    {
        return clubName;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    public String getStatusName()
    {
        return statusName;
    }

    public int getCount()
    {
        return count;
    }
}
