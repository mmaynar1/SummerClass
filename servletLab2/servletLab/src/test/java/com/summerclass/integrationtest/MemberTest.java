package com.summerclass.integrationtest;

import com.summerclass.domain.Member;
import com.summerclass.repository.MemberDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class MemberTest extends SpringIntegrationTest
{
    @Autowired
    private MemberDao memberDao;

    public static final String TEST_MEMBER_FIRST_NAME = "Opie";
    public static final String TEST_MEMBER_LAST_NAME = "McFarlan";
    public static final String TEST_MEMBER_PASSWORD = "d";
    public static final String TEST_MEMBER_USERNAME = TEST_MEMBER_PASSWORD;

    @Test
    public void testGetMembers()
    {
        List<Member> members = memberDao.getMembers();
        assertEquals( members.size(), memberDao.getCount() );
        for (Member member : members)
        {
            assertTrue( StringSupport.isGuid( member.getMemberId() ) );
        }
    }

    @Test
    public void testGetMemberByUserNameAndPassword()
    {
        Member member = memberDao.getMember( TEST_MEMBER_USERNAME, TEST_MEMBER_PASSWORD );
        assertTrue( StringSupport.isGuid( member.getMemberId() ) );
        assertTrue( member.getFirstName().equals( TEST_MEMBER_FIRST_NAME ) );
        assertTrue( member.getLastName().equals( TEST_MEMBER_LAST_NAME ) );
    }

}
