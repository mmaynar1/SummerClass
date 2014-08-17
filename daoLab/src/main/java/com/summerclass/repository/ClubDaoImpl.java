package com.summerclass.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClubDaoImpl implements ClubDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public String getClubId( int number )
    {
        String sql = "select c_id from clubs where c_number = :number";
        MapSqlParameterSource source = new MapSqlParameterSource( "number", number );
        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public int getClubCount()
    {
        String sql = "select count(*) from clubs";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, Integer.class );
    }
}
