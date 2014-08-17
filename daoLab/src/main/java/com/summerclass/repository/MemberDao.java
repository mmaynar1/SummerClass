package com.summerclass.repository;

import java.sql.SQLException;

public interface MemberDao
{
    String getMemberId( String firstName );

    int getMemberCount();

    void addMember( String firstName, String lastName );
}
