package com.summerclass.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdName implements Selectable
{
    private String id;
    private String name;

    public IdName()
    {
    }

    public static class Mapper implements RowMapper<Selectable>
    {
        public Selectable mapRow( ResultSet resultSet, int rowNumber ) throws SQLException
        {
            IdName idName = new IdName();

            idName.setId( resultSet.getString( "ID" ) );
            idName.setName( resultSet.getString( "NAME" ) );

            return idName;
        }
    }

    @Override
    public String toString()
    {
        return "IdName{" +
               "id='" + getId() + '\'' +
               ", name='" + getName() + '\'' +
               '}';
    }

    public IdName( String id, String name )
    {
        this.id = id;
        this.name = name;
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
