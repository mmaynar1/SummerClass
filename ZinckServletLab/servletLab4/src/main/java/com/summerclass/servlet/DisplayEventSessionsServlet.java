package com.summerclass.servlet;

import com.summerclass.domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DisplayEventSessionsServlet extends ServletBase
{
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( !validateSession( request ) )
        {
            forward( request, response, LOGIN_URL );
        }
        else
        {
            try
            {
                List<EventSession> eventSessions = getFilteredEvents( request );
                showEvents( request, response, null, eventSessions );
            }
            catch (Exception exception)
            {
                forwardError( request, response );
            }
        }
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( !validateSession( request ) )
        {
            forward( request, response, LOGIN_URL );
        }
        else
        {
            try
            {
                showEvents( request, response, null, getMemberEvents( request ) );
            }
            catch (Exception exception)
            {
                forwardError( request, response );
            }
        }
    }

    private List<EventSession> getFilteredEvents( HttpServletRequest request )
    {
        String member = request.getParameter( "memberFilter" );
        String employee = request.getParameter( "employeeFilter" );
        String club = request.getParameter( "clubFilter" );
        String eventType = request.getParameter( "eventTypeFilter" );
        String status = request.getParameter( "statusFilter" );
        return getEventDao().getEvents( member, employee, club, eventType, status );
    }

    private List<EventSession> getMemberEvents( HttpServletRequest request )
    {
        Member loginMember = (Member) request.getSession().getAttribute( "loginMember" );
        return getEventDao().getMemberEvents( loginMember.getMemberId() );
    }
}
