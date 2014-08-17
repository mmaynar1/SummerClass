package com.summerclass.servlet;

import com.summerclass.common.StaticSpringApplicationContext;
import com.summerclass.domain.*;
import com.summerclass.repository.*;
import com.summerclass.utility.StringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public abstract class ServletBase extends HttpServlet
{
    public final static String LOGIN_URL = "/login";
    public final String LOGIN_CREDENTIAL = "loginMember";

    public abstract void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException;

    public abstract void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException;

    final public MemberDao getMemberDao()
    {
        return StaticSpringApplicationContext.getSpringObject( MemberDao.class );
    }

    final public EventDao getEventDao()
    {
        return StaticSpringApplicationContext.getSpringObject( EventDao.class );
    }

    final public EmployeeDao getEmployeeDao()
    {
        return StaticSpringApplicationContext.getSpringObject( EmployeeDao.class );
    }

    final public ClubDao getClubDao()
    {
        return StaticSpringApplicationContext.getSpringObject( ClubDao.class );
    }

    final public EventTypeDao getEventTypeDao()
    {
        return StaticSpringApplicationContext.getSpringObject( EventTypeDao.class );
    }

    public void forward( HttpServletRequest request, HttpServletResponse response, String url ) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( url );
        dispatcher.forward( request, response );
    }

    public void forwardError( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( "/jsp/error.jsp" );
        dispatcher.forward( request, response );
    }

    public void showEvents( HttpServletRequest request,
                            HttpServletResponse response,
                            String successMessage,
                            List<EventSession> eventSessions ) throws ServletException, IOException
    {
        request.setAttribute( "events", eventSessions );
        request.setAttribute( "successMessage", successMessage );
        Member loginMember = ((Member) request.getSession().getAttribute( LOGIN_CREDENTIAL ));
        request.setAttribute( "defaultMember", concatenateName( loginMember.getFirstName(), loginMember.getLastName() ) );

        forward( request, response, "/jsp/EventSessions.jsp" );
    }

    private String concatenateName( String firstName, String lastName )
    {
        return firstName + " " + lastName;
    }

    public void addAllMembers( PrintWriter out, List<Member> members )
    {
        for (Member member : members)
        {
            addOption( out, member.getMemberId(), combineNames( member.getFirstName(), member.getLastName() ) );
        }
    }

    private void addOption( PrintWriter out, String value, String text )
    {
        out.println( "<option value=\"" + value + "\">" + text + "</option>" );
    }

    public void addAllEmployees( PrintWriter out, List<Employee> employees )
    {
        for (Employee employee : employees)
        {
            addOption( out, employee.getEmployeeId(), combineNames( employee.getFirstName(), employee.getLastName() ) );
        }
    }

    private String combineNames( String firstName, String lastName )
    {
        return lastName + ", " + firstName;
    }

    public void addAllClubs( PrintWriter out, List<Club> clubs )
    {
        for (Club club : clubs)
        {
            out.println( "<option value=\"" + club.getId() + "\">" + club.getName() + "</option>" );
        }
    }

    public void addAllEventTypes( PrintWriter out, List<EventType> events )
    {
        for (EventType event : events)
        {
            out.println( "<option value=\"" + event.getEventTypeId() + "\">" + event.getEventName() + "</option>" );
        }
    }

    public boolean validateSession( HttpServletRequest request )
    {
        Member loginMember = ((Member) (request.getSession().getAttribute( LOGIN_CREDENTIAL )));
        return loginMember != null;
    }

}
