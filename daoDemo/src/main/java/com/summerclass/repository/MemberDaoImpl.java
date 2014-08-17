package com.summerclass.repository;

import com.summerclass.domain.Company;
import com.summerclass.domain.IdName;
import com.summerclass.domain.Member;
import com.summerclass.domain.Selectable;
import com.summerclass.utility.RandomGenerator;
import com.summerclass.utility.StringSupport;
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
    public List<String> getMemberIds( String firstName )
    {
        String sql = "select m_id from members where m_first_name = :firstName order by upper( m_first_name )";

        MapSqlParameterSource source = new MapSqlParameterSource( "firstName", firstName );

        return jdbc.queryForList( sql, source, String.class );
    }

    @Override
    public String createMember( String firstName, String lastName, String agreementTypeId, String userName, String password )
    {
        String memberId = RandomGenerator.getGuid();
        String sql = "insert into members (m_id, m_first_name, m_last_name, at_id, m_username, m_password) " +
                     "values (:id, :firstName, :lastName, :agreementTypeId, :userName, :password )";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "firstName", firstName );
        source.addValue( "lastName", lastName );
        source.addValue( "id", memberId );
        source.addValue( "agreementTypeId", agreementTypeId );
        source.addValue( "userName", userName );
        source.addValue( "password", password );

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
    public List<Selectable> getAgreementTypes()
    {
        String sql = "select at_id id, at_name name from agreementTypes order by upper( at_name )";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.query( sql, source, new IdName.Mapper() );
    }
}
