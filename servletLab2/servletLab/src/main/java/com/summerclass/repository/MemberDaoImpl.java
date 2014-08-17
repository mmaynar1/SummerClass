package com.summerclass.repository;

import com.summerclass.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDaoImpl implements MemberDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Member> getMembers()
    {
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.query( Member.Mapper.QUERY, source, new Member.Mapper() );
    }

    @Override
    public String getMemberId( String firstName, String lastName )
    {
        String sql = "select m_id from members where m_first_name = :firstName and m_last_name = :lastName";
        MapSqlParameterSource source = new MapSqlParameterSource( "firstName", firstName );
        source.addValue( "lastName",lastName );
        return jdbc.query( sql, source, new StringExtractor() );
    }

    @Override
    public int getCount()
    {
        String sql = "select count(*) from members";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public Member getMember( String userName, String password )
    {
        Member member;
        String sql = Member.Mapper.QUERY + " where m_username = :userName and m_password = :password";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "userName", userName );
        source.addValue( "password", password );
        List<Member> members = jdbc.query( sql, source, new Member.Mapper() );
        if(members.size() == 1)
        {
            member = members.get( 0 );
        }
        else
        {
            member = null;
        }

        return member;
    }
}
