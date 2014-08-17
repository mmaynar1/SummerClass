package com.summerclass.repository;

import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventType;
import com.summerclass.domain.SearchFilters;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventDaoImpl implements EventDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<EventType> getEventTypes()
    {
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.query( EventType.Mapper.QUERY, source, new EventType.Mapper() );
    }

    @Override
    public int getEventTypeCount()
    {
        String sql = "select count(*) from eventTypes";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public int getEventSessionCount()
    {
        String sql = "select count(*) from eventSessions";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public String getEventTypeId( String name )
    {
        String sql = "select et_id from eventTypes where et_name = :name";
        MapSqlParameterSource source = new MapSqlParameterSource( "name", name );
        return jdbc.query( sql, source, new StringExtractor() );
    }

    @Override
    public void createEventSession( EventSession eventSession )
    {
        String sql = "insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start) " +
                     "values( getGuid(), " +
                     ":memberId, " +
                     ":employeeId, " +
                     "(select s_id from statuses where s_abc_code = :statusAbcCode), " +
                     ":clubId, " +
                     ":eventTypeId, " +
                     "now() )";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberId", eventSession.getMember().getId() );
        source.addValue( "employeeId", eventSession.getEmployee().getId() );
        source.addValue( "statusAbcCode", eventSession.getStatus().getId() );
        source.addValue( "clubId", eventSession.getClub().getId() );
        source.addValue( "eventTypeId", eventSession.getEventType().getId() );

        jdbc.update( sql, source );
    }

    @Override
    public List<EventSession> getEventSessions()
    {
        String sql = EventSession.Mapper.QUERY + " order by c.c_number";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.query( sql, source, new EventSession.Mapper() );
    }

    @Override
    public List<EventSession> getEventSessions( SearchFilters filters )
    {
        String sql = EventSession.Mapper.QUERY + "where 1 = 1 ";
        if ( !StringSupport.isEmptyString( filters.getMemberName() ) )
        {
            sql += " and concat_ws( ' ', m.m_first_name, m.m_last_name ) like :memberName ";
        }
        if ( !StringSupport.isEmptyString( filters.getClub() ) )
        {
            sql += " and c_name like :club ";
        }
        if ( !StringSupport.isEmptyString( filters.getEmployeeName() ) )
        {
            sql += " and concat_ws( ' ', e.e_first_name, e.e_last_name ) like :employeeName ";
        }
        if ( !StringSupport.isEmptyString( filters.getEventType() ) )
        {
            sql += " and et_name like :eventType ";
        }
        if ( !StringSupport.isEmptyString( filters.getStatus() ) )
        {
            sql += " and s_name like :status ";
        }
        sql += "order by c.c_number, m_name, et_name, es_start ";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberName", formatForSearch( filters.getMemberName() ) );
        source.addValue( "club", formatForSearch( filters.getClub() ) );
        source.addValue( "employeeName", formatForSearch( filters.getEmployeeName() ) );
        source.addValue( "eventType", formatForSearch( filters.getEventType() ) );
        source.addValue( "status", formatForSearch( filters.getStatus() ) );

        return jdbc.query( sql, source, new EventSession.Mapper() );
    }

    private String formatForSearch( String parameter )
    {
        return "%" + parameter + "%";
    }
}
