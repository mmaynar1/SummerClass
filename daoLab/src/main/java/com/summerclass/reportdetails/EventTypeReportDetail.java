package com.summerclass.reportdetails;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventTypeReportDetail
{
    private final String clubName;
    private final String eventTypeName;
    private final int count;

    public EventTypeReportDetail( String clubName, String eventTypeName, int count )
    {
        this.clubName = clubName;
        this.eventTypeName = eventTypeName;
        this.count = count;
    }

    public static class Mapper implements RowMapper<EventTypeReportDetail>
    {
        public static final String QUERY = "select c.c_name clubName,\n" +
                                           "et.et_name eventType,\n" +
                                           "(select count(*)\n" +
                                           "from eventSessions sub_es\n" +
                                           "where sub_es.c_id = c.c_id\n" +
                                           "and sub_es.et_id = et.et_id\n" +
                                           ") counts ";

        public EventTypeReportDetail mapRow( ResultSet resultSet, int rowNum ) throws SQLException
        {
            String clubName = resultSet.getString( "clubName" );
            String eventTypeName = resultSet.getString( "eventType" );
            int count = resultSet.getInt( "counts" );

            return  new EventTypeReportDetail( clubName, eventTypeName, count );
        }
    }

    public String getClubName()
    {
        return clubName;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    public int getCount()
    {
        return count;
    }
}
