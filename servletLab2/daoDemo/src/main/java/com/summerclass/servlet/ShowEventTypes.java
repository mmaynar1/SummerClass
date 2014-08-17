package com.summerclass.servlet;

import com.summerclass.domain.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowEventTypes extends ServletBase
{
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        loadEventTypes( request, response );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        loadEventTypes( request, response );
    }

    private void loadEventTypes( HttpServletRequest request, HttpServletResponse response ) throws IOException,  ServletException
    {
        List<EventType> eventTypes = getEventDao().getEventTypes();
        Selectable employee = getEmployeeDao().getRandomEmployee();
        Club club = getClubDao().getClub( 3000 );
        List<Club> clubs = getClubDao().getAll();
        ShowEventsBean bean = new ShowEventsBean( employee, club, eventTypes, clubs );

        request.setAttribute( "bean", bean );
        forward( request, response, "/jsp/showEventTypes.jsp" );
    }
}
