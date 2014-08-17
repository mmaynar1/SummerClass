package com.summerclass.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutServlet extends ServletBase
{
    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        logOut( request, response );
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        logOut( request, response );
    }

    public void logOut(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException
    {
        request.getSession().invalidate();
        forward( request, response, "/jsp/logOut.jsp" );
    }
}
