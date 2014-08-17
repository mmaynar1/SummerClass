package com.summerclass.domain;

import com.summerclass.utility.StringSupport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventType
{
    private String eventTypeId;
    private String eventName;

    public EventType()
    {
    }

    @Override
    public String toString()
    {
        return "EventType{" +
               "eventTypeId='" + getEventTypeId() + '\'' +
               ", eventName='" + getEventName() + '\'' +
               '}';
    }

    @Override
    public boolean equals( Object object )
    {
        boolean equal;

        if ( this == object )
        {
            equal = true;
        }
        else
        {
            EventType eventType = (EventType) object;
            equal = StringSupport.safeEqual( getEventTypeId(), eventType.getEventTypeId() ) &&
                    StringSupport.safeEqual( getEventName(), eventType.getEventName() );
        }

        return equal;
    }

    @Override
    public int hashCode()
    {
        int result = getEventTypeId() != null ? getEventTypeId().hashCode() : 0;
        result = 31 * result + (getEventName() != null ? getEventName().hashCode() : 0);
        return result;
    }

    public static class Mapper implements RowMapper<EventType>
    {
        public final static String QUERY = "select et.et_id, et.et_name from eventTypes et ";

        public EventType mapRow( ResultSet resultSet, int rowNumber ) throws SQLException
        {
            EventType eventType = new EventType();

            eventType.setEventTypeId( resultSet.getString( "ET_ID" ) );
            eventType.setEventName( resultSet.getString( "ET_NAME" ) );

            return eventType;
        }
    }

    public String getEventTypeId()
    {
        return eventTypeId;
    }

    public void setEventTypeId( String eventTypeId )
    {
        this.eventTypeId = eventTypeId;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName( String eventName )
    {
        this.eventName = eventName;
    }
}
