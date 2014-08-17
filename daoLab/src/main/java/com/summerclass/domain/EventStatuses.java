package com.summerclass.domain;

public enum EventStatuses
{

    pending("PEN"), complete("COM"), cancelled("CAN");

    //abcCode must match the one in the statuses table
    private final String abcCode;

    private EventStatuses( String abcCode )
    {
        this.abcCode = abcCode;
    }

    public String getAbcCode()
    {
        return abcCode;
    }
}
