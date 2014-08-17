package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class EventSession
{
    private String eventId;
    private Club club;
    private Selectable eventType;
    private Selectable member;
    private Selectable employee;
    private Selectable status;
    private Timestamp startDateTime;

    public static class Mapper implements RowMapper<EventSession>
    {
        public final static String QUERY = "select es.es_id, es.es_start,  " +
                                           "m.m_id, concat_ws( ' ', m.m_first_name, m.m_last_name ) m_name, " +
                                           "e.e_id, concat_ws( ' ', e.e_first_name, e.e_last_name ) e_name, " +
                                           "s.s_id, s.s_name, " +
                                           "c.c_id, c.c_number, c.c_name, " +
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

            Club club = getClub( resultSet );
            eventSession.setClub( club );

            eventSession.setMember( new IdName( resultSet.getString( "M_ID" ), resultSet.getString( "M_NAME" ) ) );
            eventSession.setEmployee( new IdName( resultSet.getString( "E_ID" ), resultSet.getString( "E_NAME" ) ) );
            eventSession.setEventType( new IdName( resultSet.getString( "ET_ID" ), resultSet.getString( "ET_NAME" ) ) );
            eventSession.setStatus( new IdName( resultSet.getString( "S_ID" ), resultSet.getString( "S_NAME" ) ) );

            return eventSession;
        }

        private Club getClub( ResultSet resultSet ) throws SQLException
        {
            String clubId = resultSet.getString( "C_ID" );
            String clubName = resultSet.getString( "C_NAME" );
            int clubNumber = resultSet.getInt( "C_NUMBER" );
            return new Club( clubNumber, clubId, clubName );
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

    public Selectable getClub()
    {
        return club;
    }

    public void setClub( Club club )
    {
        this.club = club;
    }

    public Selectable getEventType()
    {
        return eventType;
    }

    public void setEventType( Selectable eventType )
    {
        this.eventType = eventType;
    }

    public Selectable getMember()
    {
        return member;
    }

    public void setMember( Selectable member )
    {
        this.member = member;
    }

    public Selectable getEmployee()
    {
        return employee;
    }

    public void setEmployee( Selectable employee )
    {
        this.employee = employee;
    }

    public Selectable getStatus()
    {
        return status;
    }

    public void setStatus( Selectable status )
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
