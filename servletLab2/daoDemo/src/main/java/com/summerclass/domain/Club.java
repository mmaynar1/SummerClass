package com.summerclass.domain;

import com.summerclass.utility.StringSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Club implements Selectable
{
    private int number;
    private String id;
    private String name;

    @Override
    public boolean equals( Object object )
    {
        boolean equals = false;

        if ( this == object )
        {
            equals = true;
        }
        else if ( (object instanceof Club) )
        {
            Club club = (Club) object;

            equals = (getNumber() == club.getNumber() &&
                      StringSupport.safeEqual( getName(), club.getName() ) &&
                      StringSupport.safeEqual( getId(), club.getId() ));
        }

        return equals;
    }

    @Override
    public int hashCode()
    {
        final int prime = 13;

        return prime * getNumber() +
               prime * StringSupport.getHashCode( getId() ) +
               prime * StringSupport.getHashCode( getName() );
    }

    public boolean isValidClubNumber()
    {
        return (getNumber() >= 1000 & getNumber() <= 9999);
    }

    public static class Mapper implements RowMapper<Club>
    {
        public final static String QUERY = "select c_id id, c_number number, c_name name from clubs ";

        @Override
        public Club mapRow( ResultSet resultSet, int rowNum ) throws SQLException
        {
            return getClub( resultSet );
        }
    }

    public static class Extractor implements ResultSetExtractor<Club>
    {
        @Override
        public Club extractData( ResultSet resultSet ) throws SQLException, DataAccessException
        {
            Club club;

            if ( resultSet.next() )
            {
                club = getClub( resultSet );
            }
            else
            {
                club = new Club();
            }

            return club;
        }
    }

    public static class Extractor2 implements ResultSetExtractor<List<Club>>
    {
        @Override
        public List<Club> extractData( ResultSet resultSet ) throws SQLException, DataAccessException
        {
            List<Club> clubs = new ArrayList<>();

            while ( resultSet.next() )
            {
                clubs.add( getClub( resultSet ) );
            }

            return clubs;
        }
    }

    private static Club getClub( ResultSet resultSet ) throws SQLException
    {
        return new Club( resultSet.getInt( "NUMBER" ), resultSet.getString( "ID" ), resultSet.getString( "NAME" ) );
    }

    public Club()
    {
    }

    public Club( int number, String id, String name )
    {
        setId( id );
        setName( name );
        setNumber( number );
    }

    public boolean isValid()
    {
        return (StringSupport.isGuid( getId() ) &&
                !StringSupport.isEmptyString( getName() ) &&
                isValidClubNumber());
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber( int number )
    {
        this.number = number;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
}
