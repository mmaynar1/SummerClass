package com.summerclass.integrationtest;

import com.summerclass.domain.EventStatuses;
import com.summerclass.domain.Member;
import com.summerclass.reportdetails.MemberPendingEventsReportDetail;
import com.summerclass.repository.EventsDao;
import com.summerclass.repository.MemberDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.testng.Assert.*;

public class MemberTest extends SpringIntegrationTest
{
    public static final String TEST_MEMBER_FIRST_NAME = "Mitchum";
    public static final String TEST_MEMBER_LAST_NAME = "Maynard";

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private EventsDao eventsDao;

    final static private String FIRST_NAME = "qqqq";
    final static private String LAST_NAME = FIRST_NAME;

    @Test
    public void testGetMemberId()
    {
        String id = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        assertTrue( StringSupport.isGuid( id ) );

        id = memberDao.getMemberId( null );
        assertNull( id );
    }

    @Test
    public void testMemberCount()
    {
        assertTrue( memberDao.getMemberCount() > 0 );
    }

    @Test
    public void testAddMember()
    {
        int count = memberDao.getMemberCount();

        memberDao.addMember( FIRST_NAME, LAST_NAME );

        assertEquals( count + 1, memberDao.getMemberCount() );

        String id = memberDao.getMemberId( FIRST_NAME );

        assertTrue( StringSupport.isGuid( id ) );

        Member member = memberDao.getMember( id );
        assertEquals( id, member.getId() );
        assertEquals( FIRST_NAME, member.getFirstName() );
        assertEquals( LAST_NAME, member.getLastName() );
        memberDao.deleteMember( id );
    }

    @Test
    public void testGetMemberPendingEventsReportDetails()
    {
        List<MemberPendingEventsReportDetail> details = memberDao.getMemberPendingEventsReportDetails();
        int eventsCount = eventsDao.getEventsCountByStatus( EventStatuses.pending );
        assertEquals( details.size(), eventsCount );
        for (MemberPendingEventsReportDetail detail : details)
        {
            assertEquals( detail.getStatusName(), EventStatuses.pending.getName() );
        }
    }

    @Test
    public void testGetMember()
    {
        String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME );
        Member member = memberDao.getMember( memberId );
        assertEquals( memberId, member.getId() );
        assertEquals( TEST_MEMBER_FIRST_NAME, member.getFirstName() );
        assertEquals( TEST_MEMBER_LAST_NAME, member.getLastName() );

        member = memberDao.getMember( null );
        assertNotNull( member );
        assertNull( member.getId() );
        assertNull( member.getFirstName() );
        assertNull( member.getLastName() );
    }
}