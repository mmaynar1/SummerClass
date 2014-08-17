package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Member
{
    private String memberId;
    private String firstName;
    private String lastName;
    private String agreementType;
    private String agreementTypeId;
    private String username;
    private String password;

    public Member()
    {
    }

    @Override
    public String toString()
    {
        return "Member{" +
               "memberId='" + getMemberId() + '\'' +
               ", firstName='" + getFirstName() + '\'' +
               ", lastName='" + getLastName() + '\'' +
               ", agreementType='" + getAgreementType() + '\'' +
               ", username='" + getUsername() + '\'' +
               ", password='" + getPassword() + '\'' +
               '}';
    }

    public void clear()
    {
        setUsername( "" );
        setPassword( "" );
/*        List<Member> members = new ArrayList<>(  );
        members.size();
        members.get( 0 );*/
    }

    public static class Mapper implements RowMapper<Member>
    {
        public final static String QUERY = "select m.m_id, m.m_first_name, m.m_last_name, at.at_name, at.at_id, m.m_username, m.m_password " +
                                           "from members m join agreementTypes at on at.at_id = m.at_id ";

        public Member mapRow( ResultSet resultSet, int rowNumber ) throws SQLException
        {
            Member member = new Member();

            member.setMemberId( resultSet.getString( "M_ID" ) );
            member.setFirstName( resultSet.getString( "M_FIRST_NAME" ) );
            member.setLastName( resultSet.getString( "M_LAST_NAME" ) );
            member.setAgreementType( resultSet.getString( "AT_NAME" ) );
            member.setAgreementTypeId( resultSet.getString( "AT_ID" ) );
            member.setUsername( resultSet.getString( "M_USERNAME" ) );
            member.setPassword( resultSet.getString( "M_PASSWORD" ) );
            return member;
        }
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId( String memberId )
    {
        this.memberId = memberId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getAgreementType()
    {
        return agreementType;
    }

    public void setAgreementType( String agreementType )
    {
        this.agreementType = agreementType;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getAgreementTypeId()
    {
        return agreementTypeId;
    }

    public void setAgreementTypeId( String agreementTypeId )
    {
        this.agreementTypeId = agreementTypeId;
    }
}
