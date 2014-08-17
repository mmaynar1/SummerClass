package com.summerclass.reportdetails;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusCountReportDetail
{
    private final String statusName;
    private final int count;

    public StatusCountReportDetail( String statusName, int count )
    {
        this.statusName = statusName;
        this.count = count;
    }

    public static class Mapper implements RowMapper<StatusCountReportDetail>
    {
        public static final String QUERY = "select s_name, " +
                                           "(select count(*) " +
                                           "from eventSessions es " +
                                           "where  es.s_id = s.s_id) as cnt";

        public StatusCountReportDetail mapRow( ResultSet resultSet, int rowNum ) throws SQLException
        {
            String statusName = resultSet.getString( "s_name" );
            int count = resultSet.getInt( "cnt" );

            StatusCountReportDetail detail = new StatusCountReportDetail( statusName, count );

            return detail;
        }
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
