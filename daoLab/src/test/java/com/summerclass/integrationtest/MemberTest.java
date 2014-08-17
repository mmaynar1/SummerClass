package com.summerclass.integrationtest;

import com.summerclass.repository.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MemberTest extends SpringIntegrationTest
{
    public static final String TEST_MEMBER_FIRST_NAME = "Mitchum";

    @Autowired
    private MemberDao memberDao;

    @Test
    public void testGetMemberId()
    {
        String id = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        assertFalse( id.equals( "adsf" ) );
        assertEquals( id.length(), 32 );
    }

    @Test
    public void testClubCount()
    {
        assertTrue( memberDao.getMemberCount() > 0 );
    }

    @Test
    public void testAddMember()
    {
        String firstName = "qqqq";
        String lastName = firstName;

        int count = memberDao.getMemberCount();

        memberDao.addMember( firstName, lastName );

        assertEquals( count + 1, memberDao.getMemberCount() );

        String id = memberDao.getMemberId( firstName );

        assertNotNull( id );
        assertEquals( id.length(), 32 );
    }


}
