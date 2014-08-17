package com.summerclass.repository;

import com.summerclass.domain.Employee;
import com.summerclass.domain.EventSession;
import com.summerclass.domain.IdName;
import com.summerclass.domain.Member;
import com.summerclass.utility.RandomGenerator;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public List<String> getMemberIds( String firstName )
    {
        String sql = "select m_id from members where m_first_name = :firstName order by upper( m_first_name )";

        MapSqlParameterSource source = new MapSqlParameterSource( "firstName", firstName );

        return jdbc.queryForList( sql, source, String.class );
    }

    @Override
    public String createMember( String firstName, String lastName, String agreementTypeId )
    {
        String memberId = RandomGenerator.getGuid();
        String sql = "insert into members (m_id, m_first_name, m_last_name, at_id) " +
                     "values (:id, :firstName, :lastName, :agreementTypeId )";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "firstName", firstName );
        source.addValue( "lastName", lastName );
        source.addValue( "id", memberId );
        source.addValue( "agreementTypeId", agreementTypeId );

        jdbc.update( sql, source );

        return memberId;
    }

    @Override
    public List<Member> getAll( String memberName )
    {
        String sql = Member.Mapper.QUERY;
        MapSqlParameterSource source = new MapSqlParameterSource();

        if ( !StringSupport.isEmptyString( memberName ) )
        {
            sql += " where (upper( m.m_first_name ) like :name or upper( m.m_last_name ) like :name ) ";
            source.addValue( "name", "%" + memberName.toUpperCase() + "%" );
        }

        sql += " order by upper( m.m_last_name ), upper( m.m_first_name ), m.m_id";


        return jdbc.query( sql, source, new Member.Mapper() );
    }

    @Override
    public List<Member> getAll()
    {
        String sql = Member.Mapper.QUERY + " order by upper( m.m_last_name ), upper( m.m_first_name ), m.m_id";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.query( sql, source, new Member.Mapper() );
    }

    @Override
    public String getMemberId( String firstName, String lastName )
    {

        String sql = "select m_id from members where m_first_name = :firstName and m_last_name = :lastName";

        MapSqlParameterSource source = new MapSqlParameterSource( "firstName", firstName );
        source.addValue( "lastName", lastName );
        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public Member getMember( String username, String password )
    {
        String query = Member.Mapper.QUERY + " where m_username = :username and m_password = :password";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "username", username );
        source.addValue( "password", password );
        return jdbc.queryForObject( query, source, new Member.Mapper() );
    }

    @Override
    public void createMember( Member member )
    {
        String sql = "insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) \n" +
                     "values( :guid, :firstName, :lastName, :username, :password, :agreementTypeId)";

        MapSqlParameterSource source = new MapSqlParameterSource();

        final String guid = RandomGenerator.getGuid();
        member.setMemberId( guid );
        source.addValue( "guid", guid );

        source.addValue( "firstName", member.getFirstName() );
        source.addValue( "lastName", member.getLastName() );

        source.addValue( "username", member.getUsername() );
        source.addValue( "password", member.getPassword() );

        source.addValue( "agreementTypeId", member.getAgreementTypeId() );

        jdbc.update( sql, source );
    }

    @Override
    public Member getMember( String username )
    {
        try
        {
            String query = Member.Mapper.QUERY + " where m_username = :username";
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue( "username", username );
            return jdbc.queryForObject( query, source, new Member.Mapper() );
        }
        catch (DataAccessException exception)
        {
            return null;
        }
    }

    @Override
    public List<Member> getMembers( Member member )
    {
        String sql = Member.Mapper.QUERY + " where 1 ";
        MapSqlParameterSource source = new MapSqlParameterSource();

        if ( !member.getFirstName().isEmpty() )
        {
            sql += " and m.m_first_name like :firstName";
            source.addValue( "firstName", "%" + member.getFirstName() + "%" );
        }
        if ( !member.getLastName().isEmpty() )
        {
            sql += " and m.m_last_name like :lastName";
            source.addValue( "lastName", "%" + member.getLastName() + "%" );
        }
        if ( !member.getAgreementTypeId().isEmpty() )
        {
            sql += " and m.at_id like :agreementId";
            source.addValue( "agreementId", "%" + member.getAgreementTypeId() + "%" );
        }

        return jdbc.query( sql, source, new Member.Mapper() );
    }

    @Override
    public Member getMemberById( String memberId )
    {
        String sql = Member.Mapper.QUERY + " where m.m_id = :memberId ";
        MapSqlParameterSource source = new MapSqlParameterSource( "memberId", memberId );
        return jdbc.queryForObject( sql, source, new Member.Mapper() );
    }

    @Override
    public void updateMember( Member member )
    {
        String sql = "Update members " +
                     "Set m_first_name= :firstName, " +
                     "m_last_name=:lastName, " +
                     "m_username=:username, " +
                     "m_password=:password, " +
                     "at_id=:agreementTypeId " +
                     "where m_id=:memberId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "firstName", member.getFirstName() );
        source.addValue( "lastName", member.getLastName() );
        source.addValue( "username", member.getUsername() );
        source.addValue( "password", member.getPassword() );
        source.addValue( "agreementTypeId", member.getAgreementTypeId() );
        source.addValue( "memberId", member.getMemberId() );

        jdbc.update( sql, source );
    }

    @Override
    public Member getMemberByUsername( String username )
    {
        String sql = Member.Mapper.QUERY + " where m_username=:username";
        MapSqlParameterSource source = new MapSqlParameterSource( "username", username );
        return jdbc.queryForObject( sql, source, new Member.Mapper() );
    }

    @Override
    public boolean deleteMember( String memberId )
    {
        String sql = "Delete from members where m_id = :memberId";
        MapSqlParameterSource source = new MapSqlParameterSource( "memberId", memberId );
        return jdbc.update( sql, source ) == 1;
    }

    @Override
    public int getEventsCount( String memberId )
    {
        String sql = "select count(*) from eventSessions es " +
                     "where es.m_id = :memberId";
        MapSqlParameterSource source = new MapSqlParameterSource( "memberId", memberId );
        return jdbc.queryForObject( sql, source, Integer.class );
    }
}
