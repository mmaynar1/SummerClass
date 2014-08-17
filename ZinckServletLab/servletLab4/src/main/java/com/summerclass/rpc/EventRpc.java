package com.summerclass.rpc;

import com.summerclass.common.StaticSpringApplicationContext;
import com.summerclass.repository.EventDao;
import com.summerclass.utility.StringSupport;

import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventRpc
{
    public String editEvent( String eventId, String employeeId, String date, String time )
    {
        String message;
        if ( !StringSupport.isGuid( employeeId ) || !StringSupport.isGuid( eventId ) )
        {
            message = "Invalid ID";
        }
        else if ( StringSupport.isEmptyString( date ) || StringSupport.isEmptyString( time ) )
        {
            message = "Invalid Date/Time";
        }
        else
        {
            Timestamp startTime = getTimestamp( date, time );
            EventDao eventDao = StaticSpringApplicationContext.getSpringObject( EventDao.class );
            eventDao.editEvent( eventId, employeeId, startTime );
            message = "Event Created!";
        }

        return message;
    }

    private Timestamp getTimestamp( String date, String time )
    {
        //date format: MM/dd/yyyy
        //time format: HH:mm A( or P)M
        Date dateForDB;
        String startTimeString = "";
        try
        {
            String justTime = time.substring( 0, 5 );
            startTimeString = date + " " + justTime + ":00";
            DateFormat format = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );

            dateForDB = format.parse( startTimeString );
        }
        catch (Exception e)
        {
            throw new RuntimeException( "Couldn't parse " + startTimeString );
        }

        long timeInMillis = dateForDB.getTime() + getAdjustmentForAmOrPm( time );
        return new Timestamp( timeInMillis );
    }

    private long getAdjustmentForAmOrPm( String time )
    {
        String amOrPm = time.substring( 6 );
        long adjustmentForAmOrPm = 0;
        if ( amOrPm.equals( "PM" ) )
        {
            adjustmentForAmOrPm = 43200000;
        }
        return adjustmentForAmOrPm;
    }

}
