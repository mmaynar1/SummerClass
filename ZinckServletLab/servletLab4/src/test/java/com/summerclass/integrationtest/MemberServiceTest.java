package com.summerclass.integrationtest;

import com.summerclass.domain.IdName;
import com.summerclass.domain.Member;
import com.summerclass.domain.Result;
import com.summerclass.repository.AgreementTypesDao;
import com.summerclass.repository.MemberDao;
import com.summerclass.service.MemberService;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class MemberServiceTest extends SpringIntegrationTest
{
    @Autowired
    private MemberService memberService;

    @Autowired
    private AgreementTypesDao agreementTypesDao;

    @Autowired
    private MemberDao memberDao;

    @Test
    public void testUpdateMember()
    {
        final String username = "Leon";
        final String firstName = "Jake";
        Member member = memberDao.getMemberByUsername( username );

        assertTrue( StringSupport.isGuid( member.getMemberId() ) );

        final String originalFirstName = member.getFirstName();

        member.setFirstName( firstName );

        Result result = memberService.updateMember( member );
        assertTrue( result.isWorked() );

        Member updatedMember = memberDao.getMemberByUsername( username );

        assertTrue( StringSupport.isGuid( updatedMember.getMemberId() ) );

        assertTrue( updatedMember.getMemberId().equals( member.getMemberId() ) );

        assertTrue( updatedMember.getFirstName().equals( firstName ) );

        member.setFirstName( originalFirstName );
        result = memberService.updateMember( member );
        assertTrue( result.isWorked() );
    }

    @Test
    public void testInsertExistingUsername()
    {
        boolean failed = false;
        try
        {
            final String username = "bobby";
            Member member = memberDao.getMemberByUsername( username );
            member.setUsername( "johnnyboy" );
            memberDao.updateMember( member );
        }
        catch (DataAccessException exception)
        {
            failed = true;
        }

        assertTrue( failed );
    }

    @Test
    public void testAgreementTypes()
    {
        List<IdName> agreementTypes = (List)agreementTypesDao.getAllAgreementTypesIdName();
        for (IdName agreementType : agreementTypes)
        {
            assertTrue( StringSupport.isGuid( agreementType.getId() ) );
        }
    }
}
