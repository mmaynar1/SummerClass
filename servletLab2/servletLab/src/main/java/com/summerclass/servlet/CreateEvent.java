package com.summerclass.servlet;

import com.summerclass.domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateEvent extends ServletBase
{
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        displayCreateEventPage( request, response );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        displayCreateEventPage( request, response );
    }

    private void displayCreateEventPage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( isLoggedIn( request, response ) )
        {
            CreateEventBean bean = new CreateEventBean();
            String memberName = request.getSession().getAttribute( "firstName" ) + " " + request.getSession().getAttribute( "lastName" );
            bean.setMemberName( memberName );
            List<IdName> employees = new ArrayList<>();
            for (Employee employee : getEmployeeDao().getEmployees())
            {
                employees.add( employee.getIdName() );
            }
            bean.setEmployees( employees );

            List<IdName> clubs = new ArrayList<>();
            for (Club club : getClubDao().getClubs())
            {
                clubs.add( club.getIdName() );
            }
            bean.setClubs( clubs );

            List<IdName> eventTypes = new ArrayList<>();
            for (EventType eventType : getEventDao().getEventTypes())
            {
                eventTypes.add( eventType.getIdName() );
            }
            bean.setEventTypes( eventTypes );

            bean.setUri( request.getServletPath() );
            request.setAttribute( "bean", bean );
            String url = "/jsp/createEvent.jsp";
            forward( request, response, url );
        }
        else
        {
            redirectToLoginPage( request, response );
        }
    }
}
