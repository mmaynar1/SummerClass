package com.summerclass.integrationtest;

import com.summerclass.domain.Company;
import com.summerclass.domain.CompanyImpl;
import com.summerclass.domain.Result;
import com.summerclass.utility.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testng.Assert.*;

// todo add these imports

public class ControllerTest extends SpringIntegrationTest
{
    @Autowired
    private ControllerDao controllerDao;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddEventTypeNoName() throws Exception
    {
        Map<String, String> parameters = new HashMap<>();

        final String url = "/addEventType.spr";
        final boolean worked = false;
        final String text = "Name is a required field";
        verifyControllerResult( worked, text, parameters, url, "jsp/addEventType.jsp" );
    }

    @Test
    public void testAddEventType() throws Exception
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "eventName", RandomGenerator.getName() );

        final String url = "/addEventType.spr";
        final boolean worked = true;
        final String text = "Added";
        verifyControllerResult( worked, text, parameters, url, "jsp/addEventType.jsp" );
    }


    @Test
    public void testAddEmployee() throws Exception
    {
        Map<String, String> parameters = new HashMap<>();
        parameters.put( "firstName", "Hank" );
        parameters.put( "lastName", "Williams" );

        final String url = "/addEmployee.spr";
        final boolean worked = true;
        final String text = "Added";
        verifyControllerResult( worked, text, parameters, url, "jsp/addEmployee.jsp" );
    }

    private void verifyControllerResult( boolean worked,
                                         String text,
                                         Map<String, String> parameters,
                                         String url,
                                         String expectedView ) throws Exception
    {
        verifyControllerResult1( worked, text, parameters, url, expectedView );
    }

    // standard
    private void verifyControllerResult2( boolean worked,
                                          String text,
                                          Map<String, String> parameters,
                                          String url,
                                          String expectedView ) throws Exception
    {
        final Object body = null;
        mockMvc.perform( controllerDao.buildRequest( HttpMethod.POST, url, parameters, body ) )
                .andExpect( status().isOk() )
                .andExpect( view().name( expectedView ) )
                .andExpect( model().attribute( "result", any( Result.class ) ) )
                .andExpect( model().attribute( "result", allOf(
                        hasProperty( "worked", is( worked ) ),
                        hasProperty( "message", containsString( text ) ) ) ) );
    }

    private void verifyControllerResult1( boolean worked,
                                          String text,
                                          Map<String, String> parameters,
                                          String url,
                                          String expectedView ) throws Exception
    {
        Object body = null;
        final String key = "result";
        MockHttpServletRequestBuilder requestBuilder = controllerDao.buildRequest( HttpMethod.POST, url, parameters, body );
        MockHttpSession session = new MockHttpSession();
        session.setAttribute( "loggedIn", "1" );

        Company company = new CompanyImpl();
        company.setName( "It worked" );
        session.setAttribute( "company", company );

        ResultActions resultActions = mockMvc.perform( requestBuilder.session( session ) );

        MvcResult mvcResult = resultActions.andReturn();
        assertTrue( mvcResult.getResponse().getStatus() == HttpServletResponse.SC_OK );

        ModelAndView modelAndView = mvcResult.getModelAndView();
        String view = modelAndView.getViewName();
        assertEquals( view, expectedView );

        Map<String, Object> model = modelAndView.getModel();
        Result result = (Result) model.get( key );
        assertNotNull( result );

        assertTrue( result.isWorked() == worked );
        assertNotNull( result.getMessage() );
        assertTrue( result.getMessage().contains( text ) );
    }
}
