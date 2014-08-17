package com.summerclass.repository;

import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventType;
import com.summerclass.domain.SearchFilters;

import java.util.List;

public interface EventDao
{
    List<EventType> getEventTypes();

    int getEventTypeCount();

    int getEventSessionCount();

    String getEventTypeId( String name );

    void createEventSession( EventSession eventSession );

    List<EventSession> getEventSessions();

    List<EventSession> getEventSessions( SearchFilters filters);
}
