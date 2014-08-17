package com.summerclass.domain;

import java.util.List;

public class ShowEventsBean
{
    private String securityNotice;
    private Selectable employee;
    private Club club;
    private List<EventType> eventTypes;
    private List<Club> clubs;

    public ShowEventsBean()
    {
    }

    public ShowEventsBean( Selectable employee, Club club, List<EventType> eventTypes, List<Club> clubs )
    {
        this.employee = employee;
        this.club = club;
        this.eventTypes = eventTypes;
        this.clubs = clubs;
        this.securityNotice = "This page is for internal use only.";
    }

    public Selectable getEmployee()
    {
        return employee;
    }

    public void setEmployee( Selectable employee )
    {
        this.employee = employee;
    }

    public Club getClub()
    {
        return club;
    }

    public void setClub( Club club )
    {
        this.club = club;
    }

    public List<EventType> getEventTypes()
    {
        return eventTypes;
    }

    public void setEventTypes( List<EventType> eventTypes )
    {
        this.eventTypes = eventTypes;
    }

    public List<Club> getClubs()
    {
        return clubs;
    }

    public void setClubs( List<Club> clubs )
    {
        this.clubs = clubs;
    }

    public String getSecurityNotice()
    {
        return securityNotice;
    }

    public void setSecurityNotice( String securityNotice )
    {
        this.securityNotice = securityNotice;
    }
}
