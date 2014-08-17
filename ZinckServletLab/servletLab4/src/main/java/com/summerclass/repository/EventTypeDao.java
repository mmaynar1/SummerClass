package com.summerclass.repository;

import com.summerclass.domain.EventType;
import com.summerclass.domain.IdName;

import java.util.List;

public interface EventTypeDao
{
    List<EventType> getAllEventTypes();

    String getEventTypeId( String name );

    List<Object> getAllEventTypesIdName();
}
