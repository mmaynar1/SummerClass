package com.summerclass.repository;

import com.summerclass.domain.EventStatuses;
import com.summerclass.domain.Member;
import com.summerclass.reportdetails.MemberPendingEventsReportDetail;
import com.summerclass.utility.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberDaoImpl implements MemberDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public String getMemberId( String firstName )
    {
        String sql = "select m_id from members where m_first_name = :firstName";
        MapSqlParameterSource source = new MapSqlParameterSource( "firstName", firstName );
        return jdbc.query( sql, source, new StringExtractor() );
    }

    @Override
    public Member getMember( String memberId )
    {
        String sql = Member.Extractor.QUERY + " from members where m_id = :memberId";
        MapSqlParameterSource source = new MapSqlParameterSource( "memberId", memberId );
        return jdbc.query( sql, source, new Member.Extractor() );
    }


    @Override
    public int getMemberCount()
    {
        String sql = "select count(*) from members";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public void addMember( String firstName, String lastName )
    {
        String sql = "insert into members (m_id, m_first_name, m_last_name) values( :memberId, :firstName, :lastName )";

        String memberId = RandomGenerator.getGuid();

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "memberId", memberId );
        source.addValue( "firstName", firstName );
        source.addValue( "lastName", lastName );

        int rowsUpdated = jdbc.update( sql, source );

        if ( rowsUpdated != 1 )
        {
            throw new RuntimeException( "Error occurred while attempting to insert new member record" );
        }
    }

    @Override
    public List<MemberPendingEventsReportDetail> getMemberPendingEventsReportDetails()
    {
        String sql = MemberPendingEventsReportDetail.Mapper.QUERY +
                     " from eventSessions es " +
                     "join members m on m.m_id = es.m_id " +
                     "join employees e on e.e_id = es.e_id " +
                     "join clubs c on c.c_id = es.c_id " +
                     "join eventTypes et on et.et_id = es.et_id " +
                     "join statuses s on s.s_id = es.s_id " +
                     "where s.s_id = (select s_id from statuses where s_abc_code = :abcCode) " +
                     "order by member_name, c_number, es_start";

        MapSqlParameterSource source = new MapSqlParameterSource( "abcCode", EventStatuses.pending.getAbcCode() );

        return jdbc.query( sql, source, new MemberPendingEventsReportDetail.Mapper() );

    }


    @Override
    public boolean deleteMember( String memberId )
    {
        String sql = "delete from members where m_id = :memberId";

        MapSqlParameterSource source = new MapSqlParameterSource( "memberId", memberId );

        return (jdbc.update( sql, source ) > 0);
    }
}
