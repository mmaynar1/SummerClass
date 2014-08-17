package com.summerclass.repository;

import com.summerclass.utility.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

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
        return jdbc.queryForObject( sql, source, String.class );
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
        source.addValue("lastName",lastName);

        int rowsUpdated = jdbc.update( sql, source );

        if(rowsUpdated != 1)
        {
            throw new RuntimeException( "Error occurred while attempting to insert new member record" );
        }
    }
}
