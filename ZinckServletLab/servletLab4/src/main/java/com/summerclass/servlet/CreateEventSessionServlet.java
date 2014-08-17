package com.summerclass.servlet;

import com.summerclass.domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CreateEventSessionServlet extends ServletBase
{
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        /*try
        {
            if ( !validateSession( request ) )
            {
                forward( request, response, LOGIN_URL );
            }
            else
            {
                // todo try catch
                String successMessage = addEventSession( request );

                List<EventSession> eventSessions = getAllEvents();
                showEvents( request, response, successMessage, eventSessions );
            }
        }
        catch (Exception exception)
        {
            forwardError( request, response );
        }*/
        doGet( request, response );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        try
        {
            forwardRequest( request, response );
        }
        catch (Exception exception)
        {
            forwardError( request, response );
        }
    }

    private void forwardRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( !validateSession( request ) )
        {
            forward( request, response, LOGIN_URL );
        }
        else
        {
            createEventSessionPage( request, response );
        }
    }

    public String addEventSession( HttpServletRequest request ) throws ServletException, IOException
    {
        Member loginMember = (Member) request.getSession().getAttribute( "loginMember" );
        String memberId = loginMember.getMemberId();

        String employeeId = request.getParameter( "employee" );
        String clubId = request.getParameter( "club" );
        String eventTypeId = request.getParameter( "eventType" );

        int rowsAffected = getEventDao().createEvent( memberId, employeeId, clubId, eventTypeId );

        String successMessage;

        if ( rowsAffected == 1 )
        {
            successMessage = "Successfully created event session";
        }
        else
        {
            successMessage = "An error occured while creating event session " +
                             "(Must select an option in ALL fields)";
        }

        return successMessage;
    }

    private void createEventSessionPage( HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException
    {
        List<IdName> employees = (List)getEmployeeDao().getAllEmployeesIdName();

        List<IdName> clubs = (List)getClubDao().getAllClubIdName();

        List<IdName> eventTypes = (List)getEventTypeDao().getAllEventTypesIdName();

        request.setAttribute( "employees", employees );
        request.setAttribute( "clubs", clubs );
        request.setAttribute( "eventTypes", eventTypes );

        request.setAttribute( "currentMember", getMemberIdName( request ) );

        forward( request, response, "/jsp/CreateEventSession.jsp" );
    }

    private IdName getMemberIdName( HttpServletRequest request )
    {
        Member loginMember = (Member) request.getSession().getAttribute( LOGIN_CREDENTIAL );
        IdName member = new IdName(  );
        member.setId( loginMember.getMemberId() );
        member.setName( loginMember.getFirstName() + " " + loginMember.getLastName() );
        return member;
    }

    private List<EventSession> getAllEvents()
    {
        return getEventDao().getAllEvents();
    }
}
