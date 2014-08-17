package com.summerclass.integrationtest;

import com.summerclass.domain.IdName;
import com.summerclass.domain.Member;
import com.summerclass.domain.Result;
import com.summerclass.repository.*;
import com.summerclass.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class MemberControllerTest extends SpringIntegrationTest
{
    @Autowired
    private ControllerDao controllerDao;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgreementTypesDaoTest agreementTypesDaoTest;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ClubDao clubDao;

    @Autowired
    private EventTypeDao eventTypeDao;

    @Autowired
    private MemberService memberService;

    public static final String TEST_MEMBER_FIRST_NAME = "Kenneth";
    public static final String TEST_MEMBER_LAST_NAME = "Teeter";
    public static final String TEST_EMPLOYEE_FIRST_NAME = "Andy";
    public static final String TEST_EMPLOYEE_LAST_NAME = "Smith";

    @Test
    public void testGetMemberEventsCount() throws Exception
    {
        String memberId = memberDao.getMemberId( TEST_MEMBER_FIRST_NAME, TEST_MEMBER_LAST_NAME );
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "ajax", "1" );
        parameters.put( "memberId", memberId );

        Object body = null;
        String url = "/ajax/memberEventsCount.spr";
        int countBefore = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, Integer.class );
        memberService.createEvent( memberId,
                                   employeeDao.getEmployeeId( TEST_EMPLOYEE_FIRST_NAME, TEST_EMPLOYEE_LAST_NAME ),
                                   clubDao.getClubId( 3000 ),
                                   eventTypeDao.getEventTypeId( "Kick Boxing" ) );
        int countAfter = controllerDao.doRequest( HttpMethod.POST, url, parameters, body, Integer.class );
        assertEquals( countBefore + 1, countAfter );
    }

//todo fix these tests
/*
    @Test
    public void testAddMember() throws Exception
    {
        final String url = "/addMember";
        final String firstName = "Bob";
        final String lastName = "Jones";
        final String username = "bobJones";
        final String password = "password";

        Map<String, String> parameters = new HashMap<>();
        parameters.put( "firstName", firstName );
        parameters.put( "lastName", lastName );
        parameters.put( "agreementTypeId", agreementTypesDaoTest.getRandomAgreementTypeId() );
        parameters.put( "username", username );
        parameters.put( "password", password );

        final boolean worked = true;
        final String text = "Added " + firstName + " " + lastName + " with id=";

        ModelAndView modelAndView = verifyControllerResult1( parameters, url );
        verifyAddMemberPost( worked, text, modelAndView, "jsp/addMember.jsp" );
    }

    @Test
    public void testAddExistingMember() throws Exception
    {
        final String url = "/addMember";
        final String firstName = "Bob";
        final String lastName = "Jones";
        final String username = "bobby";
        final String password = "password";

        Map<String, String> parameters = new HashMap<>();
        parameters.put( "firstName", firstName );
        parameters.put( "lastName", lastName );
        parameters.put( "agreementTypeId", agreementTypesDaoTest.getRandomAgreementTypeId() );
        parameters.put( "username", username );
        parameters.put( "password", password );

        final boolean worked = false;
        final String text = "Username is already in use";

        ModelAndView modelAndView = verifyControllerResult1( parameters, url );
        verifyAddExistingMemberPost( worked, text, modelAndView, "jsp/addMember.jsp" );
    }
*/

    private ModelAndView verifyControllerResult1( Map<String, String> parameters, String url ) throws Exception
    {
        Object body = null;

        MockHttpServletRequestBuilder requestBuilder = controllerDao.buildRequest( HttpMethod.POST, url, parameters, body );
        ResultActions resultActions = mockMvc.perform( requestBuilder );

        MvcResult mvcResult = resultActions.andReturn();
        assertTrue( mvcResult.getResponse().getStatus() == HttpServletResponse.SC_OK );

        return mvcResult.getModelAndView();
    }

    private void verifyAddExistingMemberPost( boolean worked, String text, ModelAndView modelAndView, String expectedView )
    {
        final String key = "result";
        String view = modelAndView.getViewName();
        assertEquals( view, expectedView );

        Map<String, Object> model = modelAndView.getModel();
        Result result = (Result) model.get( key );
        assertNotNull( result );

        assertTrue( result.isWorked() == worked );
        assertNotNull( result.getMessage() );
        assertTrue( result.getMessage().contains( text ) );
    }

    private void verifyAddMemberPost( boolean worked, String text, ModelAndView modelAndView, String expectedView )
    {
        final String key = "result";
        final String memberKey = "member";

        String view = modelAndView.getViewName();
        assertEquals( view, expectedView );

        Map<String, Object> model = modelAndView.getModel();
        Member member = (Member) model.get( memberKey );
        assertNotNull( member );
        text += member.getMemberId();

        Result result = (Result) model.get( key );
        assertNotNull( result );

        assertTrue( result.isWorked() == worked );
        assertNotNull( result.getMessage() );
        assertTrue( result.getMessage().contains( text ) );

        assertTrue( memberDao.deleteMember( member.getMemberId() ) );
    }
}
