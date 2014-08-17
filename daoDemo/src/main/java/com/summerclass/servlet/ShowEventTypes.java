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
        request.getSession().setAttribute( "drpepper", "Dr Pepper has special stuff" );

        request.setAttribute( "coke", "Coke is It!" );

        getServletContext().setAttribute( "pepsi", "Pepsi is made of goat spit" );

        ShowEventsBean showEventsBean = getBean();

        request.setAttribute( "model", showEventsBean );

        forward( request, response, "/jsp/showEventTypes.jsp" );
    }

    private ShowEventsBean getBean()
    {
        List<EventType> eventTypes = getEventDao().getEventTypes();
        Selectable employee = getEmployeeDao().getRandomEmployee();
        Club club = getClubDao().getClub( 3000 );
        List<Club> clubs = getClubDao().getAll();
        return new ShowEventsBean( employee, club, eventTypes, clubs );
    }
}
