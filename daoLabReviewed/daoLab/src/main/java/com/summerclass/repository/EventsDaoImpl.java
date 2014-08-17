package com.summerclass.repository;

import com.summerclass.domain.EventSession;
import com.summerclass.domain.EventStatuses;
import com.summerclass.reportdetails.EventTypeAndStatusReportDetail;
import com.summerclass.reportdetails.EventTypeReportDetail;
import com.summerclass.reportdetails.StatusCountReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class EventsDaoImpl implements EventsDao
{
    public static final String TOTAL_COLUMN_LABEL = "All";

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private ClubDao clubDao;

    @Override
    public void duplicateEventsAddingAnHour( String memberId, String fromClubId, String toClubId )
    {
        String sql = "insert into eventSessions\n" +
                     "select a.* from (select getGuid(), addtime(es_start,'0 1:0:0.0'), m_id, e_id, s_id, et_id, :toClubId from eventSessions es \n" +
                     "where es.m_id = :memberId " +
                     "and es.c_id = :fromClubId) as a";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "toClubId", toClubId );
        source.addValue( "memberId", memberId );
        source.addValue( "fromClubId", fromClubId );

        jdbc.update( sql, source );
    }

    @Override
    public void deleteEventsAtClub( String memberId, String clubId )
    {
        String sql = "delete from " +
                     "eventSessions " +
                     "where m_id = :memberId " +
                     "and c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberId", memberId );
        source.addValue( "clubId", clubId );

        jdbc.update( sql, source );
    }

    @Override
    public void deleteEvents( String memberId )
    {
        String sql = "delete from\n" +
                     "eventSessions\n" +
                     "where m_id = :memberId";

        MapSqlParameterSource source = new MapSqlParameterSource( "memberId", memberId );

        jdbc.update( sql, source );
    }

    @Override
    public void deleteEvents( String memberId, String clubId, String eventTypeId )
    {
        String sql = "delete from " +
                     "eventSessions " +
                     "where et_id = :eventTypeId " +
                     "and m_id = :memberId " +
                     "and c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberId", memberId );
        source.addValue( "eventTypeId", eventTypeId );
        source.addValue( "clubId", clubId );

        jdbc.update( sql, source );
    }

    @Override
    public int getEventsCountByStatus( String memberId, EventStatuses status )
    {
        String sql = "select count(*) as cnt from eventSessions es\n" +
                     "where es.m_id = :memberId \n" +
                     "and es.s_id = (select s_id from statuses where s_abc_code = :statusAbcCode )";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberId", memberId );
        source.addValue( "statusAbcCode", status.getAbcCode() );

        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public int getEventsCount()
    {
        String sql = "select count(*) from eventSessions es";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public int getEventsCountByStatus( EventStatuses status )
    {
        String sql = "select count(*) as cnt from eventSessions es\n" +
                     "where es.s_id = (select s_id from statuses where s_abc_code = :statusAbcCode )";

        MapSqlParameterSource source = new MapSqlParameterSource( "statusAbcCode", status.getAbcCode() );

        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public void changeEventsStatuses( String memberId, EventStatuses oldStatus, EventStatuses newStatus )
    {
        String sql = "update eventSessions es\n" +
                     "set es.s_id = (select s_id from statuses where s_abc_code = :newStatusAbcCode)\n" +
                     "where es.m_id = :memberId\n" +
                     "and es.s_id = (select s_id from statuses where s_abc_code = :oldStatusAbcCode)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberId", memberId );
        source.addValue( "oldStatusAbcCode", oldStatus.getAbcCode() );
        source.addValue( "newStatusAbcCode", newStatus.getAbcCode() );

        jdbc.update( sql, source );
    }

    @Override
    public int getEventsCount( String memberId )
    {
        String sql = "select count(*) " +
                     "from eventSessions es " +
                     "where es.m_id = :memberId";

        MapSqlParameterSource source = new MapSqlParameterSource( "memberId", memberId );

        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public void createRandomEventSessions( int limit )
    {
        String sql = "insert into eventSessions (es_id, es_start, m_id, e_id, s_id, et_id, c_id)\n" +
                     "select a.* from(select getGuid(), now(), m_id, e_id, s_id, et_id, c_id\n" +
                     "from\n" +
                     "members join\n" +
                     "employees join\n" +
                     "statuses join\n" +
                     "eventTypes join\n" +
                     "clubs\n" +
                     "order by rand()) a\n" +
                     "limit :limit";

        MapSqlParameterSource source = new MapSqlParameterSource( "limit", limit );

        jdbc.update( sql, source );
    }

    @Override
    public int getEventsCountAtClub( String memberId, String clubId )
    {
        String sql = "select count(*) as cnt " +
                     "from eventSessions es " +
                     "where es.m_id = :memberId " +
                     "and c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberId", memberId );
        source.addValue( "clubId", clubId );

        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public void addHourToStartTime( String eventTypeId, String clubId )
    {
        String sql = "update eventSessions es\n" +
                     "set es.es_start = addtime(es.es_start,'0 1:0:0.0')\n" +
                     "where es.et_id = :eventTypeId " +
                     "and es.c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "eventTypeId", eventTypeId );
        source.addValue( "clubId", clubId );

        jdbc.update( sql, source );
    }

    @Override
    public List<Timestamp> getStartTimes( String memberId, String eventTypeId, String clubId )
    {
        String sql = "select es_start from eventSessions es\n" +
                     "where es.et_id = :eventTypeId " +
                     "and  es.m_id = :memberId " +
                     "and es.c_id = :clubId " +
                     "order by 1";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "eventTypeId", eventTypeId );
        source.addValue( "clubId", clubId );
        source.addValue( "memberId", memberId );

        return jdbc.queryForList( sql, source, Timestamp.class );
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
        source.addValue( "memberId", eventSession.getMemberId() );
        source.addValue( "employeeId", eventSession.getEmployeeId() );
        source.addValue( "statusAbcCode", eventSession.getStatusAbcCode() );
        source.addValue( "clubId", eventSession.getClubId() );
        source.addValue( "eventTypeId", eventSession.getEventTypeId() );

        jdbc.update( sql, source );
    }

    @Override
    public String getEventTypeId( String name )
    {
        String sql = "select et_id from eventTypes where et_name = :name";
        MapSqlParameterSource source = new MapSqlParameterSource( "name", name );
        return jdbc.query( sql, source, new StringExtractor() );
    }

    @Override
    public int getEventTypeCount()
    {
        String sql = "select count(*) from eventTypes";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public List<StatusCountReportDetail> getStatusCountReportDetails()
    {
        String sql = StatusCountReportDetail.Mapper.QUERY +
                     " from statuses s \n" +
                     "group by s_name\n" +
                     "union\n" +
                     "select :totalColumnLabel, format(count(*),0) as cnt\n" +
                     "from eventSessions es \n" +
                     "join statuses s on s.s_id = es.s_id";

        MapSqlParameterSource source = new MapSqlParameterSource( "totalColumnLabel", TOTAL_COLUMN_LABEL );

        List<StatusCountReportDetail> details = jdbc.query( sql, source, new StatusCountReportDetail.Mapper() );
        return details;
    }

    @Override
    public EventTypeReportDetail getOneEventTypeReportDetails()
    {
        String sql = EventTypeReportDetail.Mapper.QUERY +
                     " from clubs c " +
                     "join eventTypes et " +
                     "join statuses s " +
                     "group by clubName, eventType " +
                     "order by clubName, eventType limit 1";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.query( sql, source, new EventTypeReportDetail.Extractor() );
    }

    @Override
    public List<EventTypeReportDetail> getEventTypeReportDetails()
    {
        String sql = EventTypeReportDetail.Mapper.QUERY +
                     " from clubs c " +
                     "join eventTypes et " +
                     "join statuses s " +
                     "group by clubName, eventType " +
                     "order by clubName, eventType";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.query( sql, source, new EventTypeReportDetail.Mapper() );
    }

    @Override
    public List<EventTypeAndStatusReportDetail> getEventTypeAndStatusReportDetails()
    {
        String sql = EventTypeAndStatusReportDetail.Mapper.QUERY +
                     " from clubs c " +
                     "join eventTypes et " +
                     "join statuses s " +
                     "group by clubNumber, eventType, eventStatus";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.query( sql, source, new EventTypeAndStatusReportDetail.Mapper() );
    }

    @Override
    public int getEventsCountByMemberClubAndEventType(String memberId, String clubId, String eventTypeId)
    {
        String sql = "select count(*) as cnt " +
                     "from eventSessions es " +
                     "where es.m_id = :memberId " +
                     "and c_id = :clubId " +
                     "and et_id = :eventTypeId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberId", memberId );
        source.addValue( "clubId", clubId );
        source.addValue( "eventTypeId", eventTypeId );

        return jdbc.queryForObject( sql, source, Integer.class );
    }


}
