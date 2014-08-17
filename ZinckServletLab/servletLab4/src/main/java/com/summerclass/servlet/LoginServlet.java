package com.summerclass.servlet;

import com.summerclass.domain.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends ServletBase
{
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String username = request.getParameter( "username" );
        String password = request.getParameter( "password" );

        try
        {
            Member loginMember = getMemberDao().getMember( username, password );

            System.out.println(loginMember.toString());

            request.getSession().setAttribute( "loginMember", loginMember );
            forwardMainPage( request, response );
        }
        catch (Exception exception)
        {
            forwardLoginPage( request, response );
        }
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        request.getSession().invalidate();
        forwardLoginPage( request, response );
    }

    public void forwardLoginPage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String url = "/jsp/login.jsp";
        forward( request, response, url );
    }

    private void forwardMainPage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String url = "/index";
        forward( request, response, url );
    }
}
