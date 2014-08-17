package com.summerclass.repository;

import com.summerclass.domain.EventStatuses;
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
    public void duplicateEventsAddingAnHour( String memberFirstName, int fromClubNumber, int toClubNumber )
    {
        String sql = "insert into eventSessions\n" +
                     "select a.* from (select getGuid(), addtime(es_start,'0 1:0:0.0'), m_id, e_id, s_id, et_id, :toClubId from eventSessions es \n" +
                     "where es.m_id = (select m_id from members where m_first_name = :memberFirstName)\n" +
                     "and es.c_id = (select c_id from clubs where c_number = :fromClubNumber)) as a";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "toClubId", clubDao.getClubId( toClubNumber ) );
        source.addValue( "memberFirstName", memberFirstName );
        source.addValue( "fromClubNumber", fromClubNumber );

        jdbc.update( sql, source );
    }

    @Override
    public void deleteEventsAtClub( String memberFirstName, int clubNumber )
    {
        String sql = "delete from\n" +
                     "eventSessions\n" +
                     "where m_id = (select m_id from members where m_first_name = :memberFirstName)\n" +
                     "and c_id = (select c_id from clubs where c_number = :clubNumber)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberFirstName", memberFirstName );
        source.addValue( "clubNumber", clubNumber );

        jdbc.update( sql, source );
    }

    @Override
    public void deleteEvents( String memberFirstName )
    {
        String sql = "delete from\n" +
                     "eventSessions\n" +
                     "where m_id = (select m_id from members where m_first_name = :memberFirstName)";

        MapSqlParameterSource source = new MapSqlParameterSource( "memberFirstName", memberFirstName );

        jdbc.update( sql, source );
    }

    @Override
    public void deleteEvents( String memberFirstName, int clubNumber, String eventTypeName )
    {
        String sql = "delete from\n" +
                     "eventSessions\n" +
                     "where et_id = (select et_id from eventTypes where et_name = :eventTypeName)\n" +
                     "and m_id = (select m_id from members where m_first_name = :memberFirstName)\n" +
                     "and c_id = (select c_id from clubs where c_number = :clubNumber)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberFirstName", memberFirstName );
        source.addValue( "eventTypeName", eventTypeName );
        source.addValue( "clubNumber", clubNumber );

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
    public int getEventsCount( String memberFirstName )
    {
        String sql = "select count(*) as cnt\n" +
                     "from eventSessions es\n" +
                     "where es.m_id = (select m_id from members where m_first_name = :memberFirstName)";

        MapSqlParameterSource source = new MapSqlParameterSource( "memberFirstName", memberFirstName );

        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public int getEventsCountAtClub( String memberFirstName, int clubNumber )
    {
        String sql = "select count(*) as cnt\n" +
                     "from eventSessions es\n" +
                     "where es.m_id = (select m_id from members where m_first_name = :memberFirstName)" +
                     "and c_id = (select c_id from clubs where c_number = :clubNumber)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberFirstName", memberFirstName );
        source.addValue( "clubNumber", clubNumber );

        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public void addHourToStartTime( String eventTypeName, int clubNumber )
    {
        String sql = "update eventSessions es\n" +
                     "set es.es_start = addtime(es.es_start,'0 1:0:0.0')\n" +
                     "where es.et_id = (select et_id from eventTypes where et_name = :eventTypeName)\n" +
                     "and es.c_id = (select c_id from clubs where c_number = :clubNumber)";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "eventTypeName", eventTypeName );
        source.addValue( "clubNumber", clubNumber );

        jdbc.update( sql, source );
    }

    @Override
    public List<Timestamp> getStartTimes( String memberFirstName, String eventTypeName, int clubNumber )
    {
        String sql = "select es_start from eventSessions es\n" +
                     "where es.et_id = (select et_id from eventTypes where et_name = :eventTypeName)\n" +
                     "and  es.m_id = (select m_id from members where m_first_name = :memberFirstName) " +
                     "and es.c_id = (select c_id from clubs where c_number = :clubNumber) order by 1";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "eventTypeName", eventTypeName );
        source.addValue( "clubNumber", clubNumber );
        source.addValue( "memberFirstName", memberFirstName );

        return jdbc.queryForList( sql, source, Timestamp.class );
    }


    @Override
    public void createEventSession( String memberFirstName, String employeeFirstName, String statusAbcCode, int clubNumber, String eventType )
    {
        String sql = "insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start)\n" +
                     "values( getGuid(),\n" +
                     "(select m_id from members where m_first_name = :memberFirstName),\n" +
                     "(select e_id from employees where e_first_name = :employeeFirstName),\n" +
                     "(select s_id from statuses where s_abc_code = :statusAbcCode),\n" +
                     "(select c_id from clubs where c_number = :clubNumber),\n" +
                     "(select et_id from eventTypes where et_name = :eventType),\n" +
                     "now() )";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberFirstName", memberFirstName );
        source.addValue( "employeeFirstName", employeeFirstName );
        source.addValue( "statusAbcCode", statusAbcCode );
        source.addValue( "clubNumber", clubNumber );
        source.addValue( "eventType", eventType );

        jdbc.update( sql, source );
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
}
