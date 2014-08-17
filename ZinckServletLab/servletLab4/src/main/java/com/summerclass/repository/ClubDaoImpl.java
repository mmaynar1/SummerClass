package com.summerclass.repository;

import com.summerclass.domain.Club;
import com.summerclass.domain.IdName;
import com.summerclass.domain.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClubDaoImpl implements ClubDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public String getClubId( int clubNumber )
    {
        String clubId = null;
        String sql = "select c_id from clubs where c_number = :clubNumber";
        MapSqlParameterSource source = new MapSqlParameterSource( "clubNumber", clubNumber );
        List<String> clubIds = jdbc.queryForList( sql, source, String.class );
        if ( clubIds.size() == 1 )
        {
            clubId = clubIds.get( 0 );
        }

        return clubId;
    }

    @Override
    public int getClubNumber( String clubId )
    {
        String sql = "select c_number from clubs where c_id = :clubId";
        MapSqlParameterSource source = new MapSqlParameterSource( "clubId", clubId );
        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public String getClubName( int clubNumber )
    {
        String sql = "select c_name from clubs where c_number = :clubNumber";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "clubNumber", clubNumber );

        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public String getClubNameSafe( int clubNumber )
    {
        String sql = "select c_name from clubs where c_number = :clubNumber";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "clubNumber", clubNumber );

        return jdbc.query( sql, source, new StringExtractor() );
    }

    @Override
    public String getClubName( String clubId )
    {
        String sql = "select c_name from clubs where c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "clubId", clubId );

        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public String getClubName( String clubId, int clubNumber )  // redundant for demo purpose
    {
        String sql = "select c_name from clubs where c_id = :clubId and c_number = :clubNumber";

        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue( "clubId", clubId );
        source.addValue( "clubNumber", clubNumber );

        return jdbc.queryForObject( sql, source, String.class );
    }

    @Override
    public int getClubCount()
    {
        String sql = "select count(*) from clubs";

        MapSqlParameterSource source = new MapSqlParameterSource();

        return jdbc.queryForObject( sql, source, Integer.class );
    }

    @Override
    public Club getClubSafe( String clubId )
    {
        String sql = Club.Mapper.QUERY + "where c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource( "clubId", clubId );

        return jdbc.query( sql, source, new Club.Extractor() );
    }

    @Override
    public Club getClubUnsafe( String clubId )
    {
        String sql = Club.Mapper.QUERY + "where c_id = :clubId";

        MapSqlParameterSource source = new MapSqlParameterSource( "clubId", clubId );

        return jdbc.queryForObject( sql, source, new Club.Mapper() );
    }

    @Override
    public List<Object> getAllClubIdName()
    {
        String sql = "Select c_id as ID, concat_ws(' ', c_number, c_name) as NAME from clubs";
        MapSqlParameterSource source = new MapSqlParameterSource(  );
        return jdbc.query( sql, source, new ObjectMapper( "com.summerclass.domain.IdName") );
    }

    @Override
    public List<Club> getAll()
    {
        String sql = Club.Mapper.QUERY + "order by c_number";

        MapSqlParameterSource source = null;

        return jdbc.query( sql, source, new Club.Mapper() );
    }

    @Override
    public List<Integer> getClubNumbers()
    {
        String sql = "select c_number from clubs order by c_number";

        MapSqlParameterSource source = null;

        return jdbc.queryForList( sql, source, Integer.class );
    }

    @Override
    public List<String> getIds()
    {
        String sql = "select c_id from clubs order by c_number";

        MapSqlParameterSource source = null;

        return jdbc.queryForList( sql, source, String.class );
    }
}
