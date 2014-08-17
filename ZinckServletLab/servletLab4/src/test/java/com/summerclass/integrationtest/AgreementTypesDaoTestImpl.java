package com.summerclass.integrationtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AgreementTypesDaoTestImpl implements AgreementTypesDaoTest
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public String getRandomAgreementTypeId()
    {
        String sql = "Select at_id as ID from agreementtypes order by rand() Limit 1";
        MapSqlParameterSource source = new MapSqlParameterSource();
        return jdbc.queryForObject( sql, source, String.class );
    }
}
