package com.summerclass.servlet;

import com.summerclass.domain.Member;
import com.summerclass.utility.StringSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainMenuServlet extends ServletBase
{
    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String userName = request.getParameter( "username" );
        String password = request.getParameter( "password" );
        Member member = getMemberDao().getMember( userName, password );
        if ( member != null && StringSupport.isGuid( member.getMemberId() ) )
        {
            request.getSession().setAttribute( "memberId", member.getMemberId() );
            request.getSession().setAttribute( "firstName", member.getFirstName() );
            request.getSession().setAttribute( "lastName", member.getLastName() );
            enforceLogin( request, response,"/jsp/mainMenu.jsp" );
        }
        else
        {
            response.setContentType( "text/html" );
            enforceLogin( request, response,"/jsp/mainMenu.jsp" );
        }
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        enforceLogin(request, response, "/jsp/mainMenu.jsp");
    }
}
