package com.summerclass.domain;

import java.util.List;

public class EventSessionsBean
{
    private List<EventSession> eventSessions;
    private SearchFilters filters;
    private StatusInfo eventCreatedStatus;

    public EventSessionsBean()
    {
    }

    public EventSessionsBean( List<EventSession> eventSessions )
    {
        this.eventSessions = eventSessions;
        this.filters = new SearchFilters(  );
        this.eventCreatedStatus = null;
    }

    public EventSessionsBean( List<EventSession> eventSessions, SearchFilters filters )
    {
        this.eventSessions = eventSessions;
        this.filters = filters;
        this.eventCreatedStatus = null;
    }

    public EventSessionsBean( List<EventSession> eventSessions, StatusInfo eventCreatedStatus )
    {
        this.eventSessions = eventSessions;
        this.eventCreatedStatus = eventCreatedStatus;
        this.filters = new SearchFilters(  );
    }

    public StatusInfo getEventCreatedStatus()
    {
        return eventCreatedStatus;
    }

    public void setEventCreatedStatus( StatusInfo eventCreatedStatus )
    {
        this.eventCreatedStatus = eventCreatedStatus;
    }

    public List<EventSession> getEventSessions()
    {
        return eventSessions;
    }

    public SearchFilters getFilters()
    {
        return filters;
    }

    public void setFilters( SearchFilters filters )
    {
        this.filters = filters;
    }

    public void setEventSessions( List<EventSession> eventSessions )
    {
        this.eventSessions = eventSessions;
    }
}
