package com.summerclass.repository;

import com.summerclass.domain.IdName;
import com.summerclass.domain.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgreementTypesImpl implements AgreementTypesDao
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Object> getAllAgreementTypesIdName()
    {
        String sql = "Select at_id as ID, at_name as NAME from agreementtypes";
        MapSqlParameterSource source = new MapSqlParameterSource(  );
        return jdbc.query(sql, source, new ObjectMapper( "com.summerclass.domain.IdName"));
    }
}
