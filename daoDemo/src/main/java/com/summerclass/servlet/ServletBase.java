package com.summerclass.servlet;

import com.summerclass.common.StaticSpringApplicationContext;
import com.summerclass.domain.Selectable;
import com.summerclass.repository.ClubDao;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.repository.EventDao;
import com.summerclass.repository.MemberDao;

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
    public final static String URL_BASE = "/daoDemo-1.0-exploded";

    public enum Required
    {
        yes, no
    }

    public abstract void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException;

    public abstract void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException;

    final public ClubDao getClubDao()
    {
        return StaticSpringApplicationContext.getSpringObject( ClubDao.class );
    }

    final public EmployeeDao getEmployeeDao()
    {
        return StaticSpringApplicationContext.getSpringObject( EmployeeDao.class );
    }

    final public MemberDao getMemberDao()
    {
        return StaticSpringApplicationContext.getSpringObject( MemberDao.class );
    }

    final public EventDao getEventDao()
    {
        return StaticSpringApplicationContext.getSpringObject( EventDao.class );
    }

    public void addSelect( PrintWriter out, String label, String name, List<? extends Selectable> idNames )
    {
        out.println( "<tr><td></td><td><label for='" + name + "'>" + label + "</label></td><td> " );
        out.println( "<select id='" + name + "' name='" + name + "'>" );
        for (Selectable selectable : idNames)
        {
            out.println( "<option value='" + selectable.getId() + "'>" + selectable.getName() + "</option>" );
        }
        out.println( "</select></td></tr>" );
    }

    public void forward( HttpServletRequest request, HttpServletResponse response, String url ) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( url );
        dispatcher.forward( request, response );
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

    public void addBreaks( PrintWriter out, int count )
    {
        while ( count > 0 )
        {
            out.println( "<br/>" );
            --count;
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

    public void addLogo( PrintWriter out )
    {
        out.println( "<img src='images/logo.jpg'><br/>" );
    }

    public void addMenuLink( PrintWriter out )
    {
        addLink( out, "/daoDemo-1.0-exploded/index.html", "Menu" );
    }

    public void addNewMemberLink( PrintWriter out )
    {
        addLink( out, "/daoDemo-1.0-exploded/newMember.html", "Add Another Member" );
    }

    public void addShowMembersLink( PrintWriter out )
    {
        addLink( out, "/daoDemo-1.0-exploded/showMembers.html", "Show Members" );
    }

    public void endHtml( PrintWriter out )
    {
        out.println( "</body></html>" );
    }

    public void endHeadStartBody( PrintWriter out, String header )
    {
        out.println( "</head><body>" );
        addHeader( out, header );
        addLogo( out );
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
