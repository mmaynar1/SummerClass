package com.summerclass.servlet;

import com.summerclass.common.StaticSpringApplicationContext;
import com.summerclass.domain.Club;
import com.summerclass.repository.ClubDao;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.repository.EventDao;
import com.summerclass.repository.MemberDao;
import com.summerclass.utility.StringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class ServletBase extends HttpServlet
{
    public final static String URL_BASE = "/daoDemo-1.0-exploded";

    public enum Required
    {
        yes, no
    }

    public abstract void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException;

    public abstract void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException;


    public void forward( HttpServletRequest request, HttpServletResponse response, String url ) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( url );
        dispatcher.forward( request, response );
    }

    public void enforceLogin( HttpServletRequest request, HttpServletResponse response, String url ) throws ServletException, IOException
    {
        String memberId = (String) request.getSession().getAttribute( "memberId" );
        if ( StringSupport.isGuid( memberId ) )
        {
            forward( request, response, url );
        }
        else
        {
            request.getSession().invalidate();
            forward( request, response, "/jsp/login.jsp" );
        }
    }

    public boolean isLoggedIn( HttpServletRequest request, HttpServletResponse response )
    {
        String memberId = (String) request.getSession().getAttribute( "memberId" );
        return StringSupport.isGuid( memberId );
    }

    public void redirectToLoginPage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        request.getSession().invalidate();
        forward( request, response, "/jsp/login.jsp" );
    }

    final public MemberDao getMemberDao()
    {
        return StaticSpringApplicationContext.getSpringObject( MemberDao.class );
    }

    final public EmployeeDao getEmployeeDao()
    {
        return StaticSpringApplicationContext.getSpringObject( EmployeeDao.class );
    }

    final public ClubDao getClubDao()
    {
        return StaticSpringApplicationContext.getSpringObject( ClubDao.class );
    }

    final public EventDao getEventDao()
    {
        return StaticSpringApplicationContext.getSpringObject( EventDao.class );
    }

    public void addTableHeader( PrintWriter out, String... values )
    {
        out.println( "<tr>" );
        for (String value : values)
        {
            out.println( "<th>" + value + "</th>" );
        }
        out.println( "</tr>" );
    }

    public void addTableRow( PrintWriter out, String... values )
    {
        out.println( "<tr>" );
        for (String value : values)
        {
            out.println( "<td>" + value + "</td>" );
        }
        out.println( "</tr>" );
    }

    public void addBanner( PrintWriter out )
    {
        out.println( "<div class='banner'><img src='img/abc_banner.jpg' alt='abc' width='175px' height='70px'></div>" );
    }

    public void addBreaks( PrintWriter out, int count )
    {
        if ( count > 0 )
        {
            while ( count > 0 )
            {
                out.println( "<br/>" );
                --count;
            }
        }
    }

    public void endForm( PrintWriter out )
    {
        out.println( "</form> " );
    }

    public void startForm( PrintWriter out, String url )
    {
        out.println( "<form id='form' action='" + url + "' method='post'> " );
    }


    public void endHtml( PrintWriter out )
    {
        out.println( "</body></html>" );
    }

    public void startHtml( PrintWriter out, String title )
    {
        out.println( "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'> " +
                     "<html> " +
                     "<head> " +
                     "    <title>" + title + "</title>" +
                     "    <link rel='stylesheet' type='text/css' href='stylesheets/demo.css'> " +
                     "    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js'></script> " +
                     "    <script type='text/javascript' src='javascript/common.js'></script> " );
    }

    public void addHeader( PrintWriter out, String header )
    {
        out.println( "<div class='header'>" + header + "</div>" );
    }

    public void addLink( PrintWriter out, String url, String text )
    {
        out.println( "<a href='" + url + "'>" + text + "</a>" );
    }


}
