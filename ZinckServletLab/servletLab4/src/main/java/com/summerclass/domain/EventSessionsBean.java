package com.summerclass.domain;

import java.util.List;

// todo eliminate this class
public class EventSessionsBean
{

    private List<EventSession> eventSessions;

    public EventSessionsBean()
    {
    }

    public EventSessionsBean( List<EventSession> eventSessions )
    {
        setEventSessions( eventSessions );
    }

    public List<EventSession> getEventSessions()
    {
        return eventSessions;
    }

    private void setEventSessions( List<EventSession> eventSessions )
    {
        this.eventSessions = eventSessions;
    }
}
