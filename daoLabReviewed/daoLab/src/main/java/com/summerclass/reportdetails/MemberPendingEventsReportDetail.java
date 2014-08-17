package com.summerclass.reportdetails;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberPendingEventsReportDetail
{
    private final String memberName;
    private final int clubNumber;
    private final String eventTypeName;
    private final String employeeName;
    private final String startDateTime;
    private final String statusName;

    public MemberPendingEventsReportDetail( String memberName, int clubNumber, String eventTypeName, String employeeName, String startDateTime, String statusName )
    {
        this.memberName = memberName;
        this.clubNumber = clubNumber;
        this.eventTypeName = eventTypeName;
        this.employeeName = employeeName;
        this.startDateTime = startDateTime;
        this.statusName = statusName;
    }

    public static class Mapper implements RowMapper<MemberPendingEventsReportDetail>
    {

        public static final String QUERY = "select concat(m_first_name, \" \", m_last_name) member_name, " +
                                           "c_number, " +
                                           "et_name, " +
                                           "concat(e_first_name, \" \", e_last_name) employee_name, " +
                                           "date_format(es_start, '%m/%d/%Y %h:%i %p') start_time, " +
                                           "s_name";

        @Override
        public MemberPendingEventsReportDetail mapRow( ResultSet resultSet, int rowNum ) throws SQLException
        {
            return  getDetail( resultSet );
        }
    }

    private static MemberPendingEventsReportDetail getDetail( ResultSet resultSet ) throws SQLException
    {
        return new MemberPendingEventsReportDetail( resultSet.getString( "member_name" ),
                                                    resultSet.getInt( "c_number" ),
                                                    resultSet.getString( "et_name" ),
                                                    resultSet.getString( "employee_name" ),
                                                    resultSet.getString( "start_time" ),
                                                    resultSet.getString( "s_name" ) );
    }

    public String getMemberName()
    {
        return memberName;
    }

    public int getClubNumber()
    {
        return clubNumber;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public String getStartDateTime()
    {
        return startDateTime;
    }

    public String getStatusName()
    {
        return statusName;
    }
}