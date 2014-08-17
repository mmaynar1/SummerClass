package com.summerclass.repository;

import com.summerclass.domain.EventType;
import com.summerclass.domain.IdName;
import com.summerclass.domain.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventTypeDaoImpl implements EventTypeDao
{

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<EventType> getAllEventTypes()
    {
        String sql = EventType.Mapper.QUERY;
        MapSqlParameterSource source = new MapSqlParameterSource();
        sql += " order by upper( et.et_name ), et.et_id";

        return jdbc.query( sql, source, new EventType.Mapper() );
    }

    @Override
    public String getEventTypeId( String name )
    {
        String query = "select et_id from eventTypes where et_name = :name";
        MapSqlParameterSource source = new MapSqlParameterSource( "name", name );
        return jdbc.queryForObject( query, source, String.class );
    }

    @Override
    public List<Object> getAllEventTypesIdName()
    {
        String sql = "Select et_id as ID, et_name as NAME from eventtypes";
        MapSqlParameterSource source = new MapSqlParameterSource(  );
        return jdbc.query( sql, source, new ObjectMapper( "com.summerclass.domain.IdName") );
    }
}
