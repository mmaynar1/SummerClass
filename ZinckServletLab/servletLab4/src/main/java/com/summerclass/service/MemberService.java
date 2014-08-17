package com.summerclass.service;

import com.summerclass.domain.IdName;
import com.summerclass.domain.Member;
import com.summerclass.domain.Result;

import java.util.List;

public interface MemberService
{
    Result addMember( Member member );

    List<Member> getFilteredMembers( Member member );

    List<IdName> getAllAgreementTypesIdName();

    Member getMember( String memberId );

    Result updateMember( Member member );

    int getEventsCount( String memberId );

    int createEvent( String memberId, String employeeId, String clubId, String eventTypeId );
}
