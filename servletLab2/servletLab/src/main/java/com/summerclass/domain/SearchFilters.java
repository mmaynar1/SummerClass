package com.summerclass.domain;

public class SearchFilters
{
    private  String memberName;
    private  String club;
    private  String employeeName;
    private  String eventType;
    private  String status;

    public SearchFilters( String memberName, String club, String employeeName, String eventType, String status )
    {
        this.memberName = memberName;
        this.club = club;
        this.employeeName = employeeName;
        this.eventType = eventType;
        this.status = status;
    }

    public SearchFilters()
    {
    }

    public void setMemberName( String memberName )
    {
        this.memberName = memberName;
    }

    public void setClub( String club )
    {
        this.club = club;
    }

    public void setEmployeeName( String employeeName )
    {
        this.employeeName = employeeName;
    }

    public void setEventType( String eventType )
    {
        this.eventType = eventType;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public String getClub()
    {
        return club;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public String getEventType()
    {
        return eventType;
    }

    public String getStatus()
    {
        return status;
    }
}
