package com.summerclass.repository;

import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventStatus;
import com.summerclass.domain.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class EventDaoImpl implements EventDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<EventSession> getEvents( String clubId, String employeeId )
    {
        String sql = EventSession.Mapper.QUERY + "where es.c_id = :clubId and es.e_id = :employeeId order by es.es_start";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "clubId", clubId );
        source.addValue( "employeeId", employeeId );

        return jdbc.query( sql, source, new EventSession.Mapper() );
    }

    @Override
    public List<EventSession> getAllEvents()
    {
        String sql = EventSession.Mapper.QUERY + "order by es.es_start";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.query( sql, source, new EventSession.Mapper() );
    }

    @Override
    public String getRandomEventId()
    {
        String sql = "select es_id from eventSessions order by rand() limit 1";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public String getRandomEventId( EventStatus status )
    {
        String sql = "select es.es_id from eventSessions es " +
                     "join statuses s on s.s_id = es.s_id " +
                     "where s.s_abc_code = :status " +
                     "order by rand() " +
                     "limit 1";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "status", status.getAbcCode() );

        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public String getStatusAbcCode( String statusId )
    {
        String sql = "select s.s_abc_code from statuses s where s.s_id = :statusId";

        MapSqlParameterSource source = new MapSqlParameterSource( "statusId", statusId );

        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public EventSession getEvent( String eventId )
    {
        String sql = EventSession.Mapper.QUERY + "where es_id = :eventId";

        MapSqlParameterSource source = new MapSqlParameterSource( "eventId", eventId );

        return jdbc.queryForObject( sql, source, new EventSession.Mapper() );
    }

    @Override
    public int deleteEmployeeEvents( String clubId, String employeeId )
    {
        String sql = "delete from eventSessions where e_id = :employeeId and c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "employeeId", employeeId ).addValue( "clubId", clubId );

        return jdbc.update( sql, source );
    }

    @Override
    public void setStatus( String eventId, EventStatus status )
    {
        String sql = "update eventSessions " +
                     "set s_id = (select s_id from statuses where s_abc_code = :status) " +
                     "where es_id = :eventId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "eventId", eventId );
        source.addValue( "status", status.getAbcCode() );

        int rowsAffected = jdbc.update( sql, source );
        if ( rowsAffected != 1 )
        {
            throw new RuntimeException( "Update status failed: " + eventId );
        }
    }

    @Override
    public void createEvents( String clubId, String employeeId, int count )
    {
        String sql = "insert into eventSessions ( es_id, m_id, e_id, s_id, c_id, et_id, es_start ) " +
                     "select t.* " +
                     "from ( " +
                     "select getGuid(), m_id, e.e_id, s_id, c.c_id, et_id, now() " +
                     "from members " +
                     "join employees e " +
                     "join clubs c " +
                     "join statuses " +
                     "join eventTypes " +
                     "where c.c_id = :clubId " +
                     "and e.e_id = :employeeId " +
                     "order by rand() ) t " +
                     "limit :count";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "clubId", clubId );
        source.addValue( "employeeId", employeeId );
        source.addValue( "count", count );

        int rowsAffected = jdbc.update( sql, source );
        if ( rowsAffected != count )
        {
            throw new RuntimeException( "create event session failed" );
        }
    }

    @Override
    public int createEvent( String memberId, String employeeId, String clubId, String eventTypeId )
    {
        final String ABC_CODE = "PEN";
        MapSqlParameterSource source = null;

        String sql = "insert into eventSessions ( es_id, m_id, e_id, s_id, c_id, et_id, es_start ) " +
                     "values( getGuid(), :memberId, :employeeId, (select s_id from statuses where s_abc_code = :statusAbcCode), :clubId, :eventTypeId, now() )";

        source = new MapSqlParameterSource();
        source.addValue( "memberId", memberId );
        source.addValue( "employeeId", employeeId );
        source.addValue( "statusAbcCode", ABC_CODE );
        source.addValue( "clubId", clubId );
        source.addValue( "eventTypeId", eventTypeId );

        return jdbc.update( sql, source );

    }

    @Override
    public List<String> getRandomEventIds( EventStatus status, int count )
    {
        String sql = "select es.es_id from eventSessions es " +
                     "join statuses s on s.s_id = es.s_id " +
                     "where s.s_abc_code = :status " +
                     "order by rand() " +
                     "limit :count";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "status", status.getAbcCode() );
        source.addValue( "count", count );

        return jdbc.queryForList( sql, source, String.class );
    }

    @Override
    public int getEventCount()
    {
        String sql = "select count(*) from eventSessions";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public int deleteEventSessions( List<String> eventSessionIds )
    {
        String sql = "delete from eventSessions where es_id in (:eventSessionIds)";

        MapSqlParameterSource source = new MapSqlParameterSource( "eventSessionIds", eventSessionIds );

        return jdbc.update( sql, source );
    }

    @Override
    public List<EventType> getEventTypes()
    {
        String sql = EventType.Mapper.QUERY + " order by upper( et.et_name )";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.query( sql, source, new EventType.Mapper() );
    }

    @Override
    public List<EventSession> getEvents( String member,
                                         String employee,
                                         String club,
                                         String eventType,
                                         String status )
    {
        String sql = EventSession.Mapper.QUERY + " where concat_ws( ' ', m.m_first_name, m.m_last_name ) like :member " +
                     "and concat_ws( ' ', e.e_first_name, e.e_last_name ) like :employee " +
                     "and c.c_number like :club " +
                     "and et.et_name like :eventType " +
                     "and s.s_name like :status " +
                     "order by c.c_number, et.et_name, s.s_name, es.es_start";

        MapSqlParameterSource source = new MapSqlParameterSource();

        // todo
        source.addValue( "member", "%" + member + "%" );
        source.addValue( "employee", "%" + employee + "%" );
        source.addValue( "club", "%" + club + "%" );
        source.addValue( "eventType", "%" + eventType + "%" );
        source.addValue( "status", "%" + status + "%" );

        return jdbc.query( sql, source, new EventSession.Mapper() );
    }

    @Override
    public List<EventSession> getMemberEvents( String memberId )
    {
        String sql = EventSession.Mapper.QUERY + " where m.m_id = :mId";
        MapSqlParameterSource source = new MapSqlParameterSource( "mId", memberId );
        return jdbc.query( sql, source, new EventSession.Mapper() );
    }

    @Override
    public List<EventSession> getPendingMemberEvents( String memberId )
    {
        String sql = EventSession.Mapper.QUERY + " where m.m_id = :mId and s.s_abc_code = :pending order by c.c_number, et.et_name, es.es_start ";
        MapSqlParameterSource source = new MapSqlParameterSource( "mId", memberId );
        source.addValue( "pending", EventStatus.pending.getAbcCode() );
        return jdbc.query( sql, source, new EventSession.Mapper() );
    }

    @Override
    public int editEvent( String eventId, String employeeId, Timestamp startTime )
    {
        String sql = "update eventSessions set e_id = :employeeId, es_start = :startTime where es_id = :eventId";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "eventId", eventId );
        source.addValue( "employeeId", employeeId );
        source.addValue( "startTime", startTime );
        return jdbc.update( sql, source );
    }
}
