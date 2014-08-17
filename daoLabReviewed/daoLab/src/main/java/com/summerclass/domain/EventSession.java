package com.summerclass.domain;


public class EventSession
{
    private final String memberId;
    private final String employeeId;
    private final String statusAbcCode;
    private final String eventTypeId;
    private final String clubId;

    public EventSession( String memberId, String employeeId, String statusAbcCode, String eventTypeId, String clubId )
    {
        this.memberId = memberId;
        this.employeeId = employeeId;
        this.statusAbcCode = statusAbcCode;
        this.eventTypeId = eventTypeId;
        this.clubId = clubId;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public String getEmployeeId()
    {
        return employeeId;
    }

    public String getStatusAbcCode()
    {
        return statusAbcCode;
    }

    public String getEventTypeId()
    {
        return eventTypeId;
    }

    public String getClubId()
    {
        return clubId;
    }
}
