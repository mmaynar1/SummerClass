package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public enum EventStatus
{
    pending( "Pending","PEN" ),

    complete( "Complete","COM" ),

    cancelled( "Cancelled","CAN" );

    final private String caption;
    final private String abcCode;

    EventStatus( String caption, String abcCode )
    {
        this.caption = caption;
        this.abcCode = abcCode;
    }

    public String getCaption()
    {
        return caption;
    }

    public String getAbcCode()
    {
        return abcCode;
    }
}
