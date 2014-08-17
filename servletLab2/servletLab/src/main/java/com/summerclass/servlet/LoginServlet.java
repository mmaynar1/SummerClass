package com.summerclass.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends ServletBase
{
    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        showLoginPage( request, response );
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        showLoginPage( request, response );
    }

    private void showLoginPage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        request.getSession().invalidate();
        forward( request, response, "/jsp/login.jsp" );
    }


}
