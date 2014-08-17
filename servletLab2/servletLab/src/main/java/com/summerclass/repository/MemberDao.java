package com.summerclass.repository;

import com.summerclass.domain.Member;

import java.util.List;

public interface MemberDao
{
    public List<Member> getMembers();

    String getMemberId( String firstName, String lastName );

    int getCount();

    Member getMember( String userName, String password );
}
