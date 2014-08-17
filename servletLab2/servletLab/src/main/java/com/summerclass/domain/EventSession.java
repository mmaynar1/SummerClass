package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class EventSession
{
    private String eventId;
    private IdName club;
    private IdName eventType;
    private IdName member;
    private IdName employee;
    private IdName status;
    private Timestamp startDateTime;

    public static class Mapper implements RowMapper<EventSession>
    {
        public final static String QUERY = "select es.es_id, es.es_start,  " +
                                           "m.m_id, concat_ws( ' ', m.m_first_name, m.m_last_name ) m_name, " +
                                           "e.e_id, concat_ws( ' ', e.e_first_name, e.e_last_name ) e_name, " +
                                           "s.s_id, s.s_name, " +
                                           "c.c_id, c.c_number, c_name, " +
                                           "et.et_id, et.et_name " +
                                           "from eventSessions es " +
                                           "join members m on m.m_id = es.m_id " +
                                           "join employees e on e.e_id = es.e_id " +
                                           "join statuses s on s.s_id = es.s_id " +
                                           "join clubs c on c.c_id = es.c_id " +
                                           "join eventTypes et on et.et_id = es.et_id ";

        public EventSession mapRow( ResultSet resultSet, int rowNumber ) throws SQLException
        {
            EventSession eventSession = new EventSession();

            eventSession.setEventId( resultSet.getString( "ES_ID" ) );
            eventSession.setStartDateTime( resultSet.getTimestamp( "ES_START" ) );

            eventSession.setClub( new IdName( resultSet.getString( "C_ID" ), resultSet.getString( "C_NAME" ) ) );
            eventSession.setMember( new IdName( resultSet.getString( "M_ID" ), resultSet.getString( "M_NAME" ) ) );
            eventSession.setEmployee( new IdName( resultSet.getString( "E_ID" ), resultSet.getString( "E_NAME" ) ) );
            eventSession.setEventType( new IdName( resultSet.getString( "ET_ID" ), resultSet.getString( "ET_NAME" ) ) );
            eventSession.setStatus( new IdName( resultSet.getString( "S_ID" ), resultSet.getString( "S_NAME" ) ) );

            return eventSession;
        }
    }

    public EventSession()
    {
    }

    @Override
    public String toString()
    {
        return "EventSession{" +
               "eventId='" + getEventId() + '\'' +
               ", club=" + getClub() +
               ", eventType=" + getEventType() +
               ", member=" + getMember() +
               ", employee=" + getEmployee() +
               ", status=" + getStatus() +
               ", startDateTime=" + getStartDateTime() +
               '}';
    }

    public String getEventId()
    {
        return eventId;
    }

    public void setEventId( String eventId )
    {
        this.eventId = eventId;
    }

    public IdName getClub()
    {
        return club;
    }

    public void setClub( IdName club )
    {
        this.club = club;
    }

    public IdName getEventType()
    {
        return eventType;
    }

    public void setEventType( IdName eventType )
    {
        this.eventType = eventType;
    }

    public IdName getMember()
    {
        return member;
    }

    public void setMember( IdName member )
    {
        this.member = member;
    }

    public IdName getEmployee()
    {
        return employee;
    }

    public void setEmployee( IdName employee )
    {
        this.employee = employee;
    }

    public IdName getStatus()
    {
        return status;
    }

    public void setStatus( IdName status )
    {
        this.status = status;
    }

    public Timestamp getStartDateTime()
    {
        return startDateTime;
    }

    public void setStartDateTime( Timestamp startDateTime )
    {
        this.startDateTime = startDateTime;
    }
}
