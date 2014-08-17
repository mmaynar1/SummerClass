package com.summerclass.domain;

import java.util.List;

public class CreateEventBean
{
    private String memberName;
    private List<IdName> clubs;
    private List<IdName> employees;
    private List<IdName> eventTypes;
    private String uri;

    public CreateEventBean( String memberName, List<IdName> clubs, List<IdName> employees, List<IdName> eventTypes, String uri )
    {
        this.memberName = memberName;
        this.clubs = clubs;
        this.employees = employees;
        this.eventTypes = eventTypes;
        this.uri = uri;
    }

    public CreateEventBean()
    {
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri( String uri )
    {
        this.uri = uri;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public void setMemberName( String memberName )
    {
        this.memberName = memberName;
    }

    public List<IdName> getClubs()
    {
        return clubs;
    }

    public void setClubs( List<IdName> clubs )
    {
        this.clubs = clubs;
    }

    public List<IdName> getEmployees()
    {
        return employees;
    }

    public void setEmployees( List<IdName> employees )
    {
        this.employees = employees;
    }

    public List<IdName> getEventTypes()
    {
        return eventTypes;
    }

    public void setEventTypes( List<IdName> eventTypes )
    {
        this.eventTypes = eventTypes;
    }
}
