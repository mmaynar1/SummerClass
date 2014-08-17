package com.summerclass.repository;

import com.summerclass.domain.IdName;
import com.summerclass.domain.Member;

import java.util.List;

public interface MemberDao
{
    List<String> getMemberIds( String firstName );

    String createMember( String firstName, String lastName, String agreementTypeId );

    List<Member> getAll( String memberName );

    List<Member> getAll();

    String getMemberId( String firstName, String lastName );

    Member getMember( String username, String password );

    void createMember( Member member );

    Member getMember( String username );

    List<Member> getMembers( Member member );

    Member getMemberById( String memberId );

    void updateMember( Member member );

    Member getMemberByUsername( String username );

    boolean deleteMember( String memberId );

    int getEventsCount( String memberId );
}
