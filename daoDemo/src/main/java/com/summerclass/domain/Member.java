package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Member
{
    private String memberId;
    private String firstName;
    private String lastName;
    private String agreementType;

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
               '}';
    }

    public static class Mapper implements RowMapper<Member>
    {
        public final static String QUERY = "select m.m_id, m.m_first_name, m.m_last_name, at.at_name " +
                                           "from members m join agreementTypes at on at.at_id = m.at_id ";

        public Member mapRow( ResultSet resultSet, int rowNumber ) throws SQLException
        {
            Member member = new Member();

            member.setMemberId( resultSet.getString( "M_ID" ) );
            member.setFirstName( resultSet.getString( "M_FIRST_NAME" ) );
            member.setLastName( resultSet.getString( "M_LAST_NAME" ) );
            member.setAgreementType( resultSet.getString( "AT_NAME" ) );
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
}
