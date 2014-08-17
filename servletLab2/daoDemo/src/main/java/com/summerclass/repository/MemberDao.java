package com.summerclass.repository;

import com.summerclass.domain.Selectable;
import com.summerclass.domain.Member;

import java.util.List;

public interface MemberDao
{
    List<String> getMemberIds( String firstName );

    String createMember( String firstName, String lastName, String agreementTypeId );

    List<Member> getAll( String memberName );

    List<Selectable> getAgreementTypes();
}
