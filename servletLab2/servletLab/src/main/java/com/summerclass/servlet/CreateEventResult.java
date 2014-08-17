package com.summerclass.servlet;

import com.summerclass.domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CreateEventResult extends ServletBase
{

    public static final String URL = "/jsp/showEventSessions.jsp";

    public enum SearchOptions
    {
        //formNames must match jsp/html
        member( "memberName" ), club( "clubName" ), employee( "employeeName" ), eventType( "eventTypeName" ), status( "statusName" );

        private SearchOptions( String formName )
        {
            this.formName = formName;
        }

        private final String formName;

        public String getFormName()
        {
            return formName;
        }
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( isLoggedIn( request, response ) )
        {
            SearchFilters filters = getFilters( request );

            List<EventSession> eventSessions = getEventDao().getEventSessions( filters );

            EventSessionsBean bean = new EventSessionsBean( eventSessions, filters);
            forwardWithBean( request, response, bean );
        }
        else
        {
            redirectToLoginPage( request, response );
        }
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( isLoggedIn( request, response ) )
        {
            String fromServlet = request.getParameter( "uri" );
            SearchFilters filters;
            if ( fromServlet != null && fromServlet.equals( "/test.html" ) )
            {
                StatusInfo createdEventStatus = createEventSession( request );
                String sessionMemberName = getSessionMemberName( request );

                filters = new SearchFilters();
                filters.setMemberName( sessionMemberName );
                List<EventSession> eventSessions = getEventDao().getEventSessions( filters );
                EventSessionsBean bean = new EventSessionsBean( eventSessions, createdEventStatus );

                forwardWithBean( request, response, bean );
            }
            else
            {
                filters = getFilters( request );
                List<EventSession> eventSessions = getEventDao().getEventSessions( filters );
                EventSessionsBean bean = new EventSessionsBean( eventSessions );

                forwardWithBean( request, response, bean );
            }

        }
        else
        {
            redirectToLoginPage( request, response );
        }
    }

    private void forwardWithBean( HttpServletRequest request, HttpServletResponse response, EventSessionsBean bean ) throws ServletException, IOException
    {
        request.setAttribute( "bean", bean );
        forward( request, response, URL );
    }

    private StatusInfo createEventSession( HttpServletRequest request )
    {
        String memberId = (String) request.getSession().getAttribute( "memberId" );
        String employeeId = request.getParameter( SearchOptions.employee.getFormName() );
        String clubId = request.getParameter( SearchOptions.club.getFormName() );
        String eventTypeId = request.getParameter( SearchOptions.eventType.getFormName() );

        EventSession eventSession = new EventSession();
        eventSession.setMember( new IdName( memberId, "" ) );
        eventSession.setEmployee( new IdName( employeeId, "" ) );
        eventSession.setClub( new IdName( clubId, "" ) );
        eventSession.setEventType( new IdName( eventTypeId, "" ) );
        eventSession.setStatus( new IdName( EventStatus.pending.getAbcCode(),
                                            EventStatus.pending.getCaption() ) );

        return createEventSession( eventSession );
    }

    private StatusInfo createEventSession( EventSession eventSession )
    {
        boolean created;
        String message;
        try
        {
            getEventDao().createEventSession( eventSession );
            created = true;
            message = "Event Created!";
        }
        catch (Exception exception)
        {
            created = false;
            message = "Could not create event.";
        }
        return new StatusInfo( message, created );

    }

    public SearchFilters getFilters( HttpServletRequest request )
    {
        String memberName = request.getParameter( CreateEventResult.SearchOptions.member.getFormName() );
        String sessionMemberName = getSessionMemberName( request );
        if ( memberName == null )
        {
            memberName = sessionMemberName;
        }
        String club = request.getParameter( CreateEventResult.SearchOptions.club.getFormName() );
        String employeeName = request.getParameter( CreateEventResult.SearchOptions.employee.getFormName() );
        String eventType = request.getParameter( CreateEventResult.SearchOptions.eventType.getFormName() );
        String status = request.getParameter( CreateEventResult.SearchOptions.status.getFormName() );
        return new SearchFilters( memberName, club, employeeName, eventType, status );
    }

    private String getSessionMemberName( HttpServletRequest request )
    {
        return request.getSession().getAttribute( "firstName" ) + " " + request.getSession().getAttribute( "lastName" );
    }
}