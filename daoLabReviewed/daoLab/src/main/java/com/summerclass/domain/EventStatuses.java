package com.summerclass.domain;

public enum EventStatuses
{

    pending( "PEN", "Pending" ), complete( "COM", "Complete" ), cancelled( "CAN", "Cancelled" );

    //abcCode must match the one in the statuses table
    private final String abcCode;
    private final String name;

    private EventStatuses( String abcCode, String name )
    {
        this.abcCode = abcCode;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String getAbcCode()
    {
        return abcCode;
    }
}
