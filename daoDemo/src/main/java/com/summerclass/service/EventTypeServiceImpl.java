package com.summerclass.service;

import com.summerclass.domain.EventType;
import com.summerclass.domain.Result;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.repository.EventDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventTypeServiceImpl implements  EventTypeService
{
    @Autowired
    private EventDao eventDao;

    @Override
    public Result addEventType( EventType eventType )
    {
        Result result;

        if ( StringSupport.isEmptyString( eventType.getEventName() ) )
        {
            result = new Result( Result.Status.failure, "Name is a required field." );
        }
        else
        {
            try
            {
                String employeeId = eventDao.createEventType( eventType );
                result = new Result( Result.Status.success, "Added " + eventType.getEventName() + ", id = " + employeeId );
            }
            catch (Exception exception)
            {
                result = new Result( Result.Status.failure, "Internal error: Unable to add event type." );
            }
        }

        return result;
    }
}
