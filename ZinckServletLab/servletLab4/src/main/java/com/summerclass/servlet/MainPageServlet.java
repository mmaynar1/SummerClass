package com.summerclass.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainPageServlet extends ServletBase
{
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        forwardRequest( request, response );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        forwardRequest( request, response );
    }

    private void forwardRequest( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( !validateSession( request ) )
        {
            forward( request, response, LOGIN_URL );
        }
        else
        {
            forwardMainPage( request, response );
        }
    }

    public void forwardMainPage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String url = "/jsp/index.jsp";
        forward( request, response, url );
    }
}
