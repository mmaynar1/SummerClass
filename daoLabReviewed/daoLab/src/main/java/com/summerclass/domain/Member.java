package com.summerclass.domain;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Member
{
    private final String id;
    private final String firstName;
    private final String lastName;

    public Member( String id, String firstName, String lastName )
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Member()
    {
        this( null, null, null );
    }

    public static class Extractor implements ResultSetExtractor<Member>
    {
        public static final String QUERY = "select m_id, m_first_name, m_last_name";

        @Override
        public Member extractData( ResultSet resultSet ) throws SQLException, DataAccessException
        {
            Member member;

            if ( resultSet.next() )
            {
                member = getMember( resultSet );
                if ( resultSet.next() )
                {
                    throw new RuntimeException( "Error: more than one result was returned" );
                }
            }
            else
            {
                member = new Member();
            }

            return member;
        }
    }

    private static Member getMember( ResultSet resultSet ) throws SQLException
    {
        String memberId = resultSet.getString( "m_id" );
        String memberFirstName = resultSet.getString( "m_first_name" );
        String memberLastName = resultSet.getString( "m_last_name" );

        return new Member( memberId, memberFirstName, memberLastName );
    }

    public String getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }
}