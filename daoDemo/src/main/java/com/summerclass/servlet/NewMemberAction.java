package com.summerclass.servlet;

import com.summerclass.common.StaticSpringApplicationContext;
import com.summerclass.repository.MemberDao;
import com.summerclass.utility.StringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class NewMemberAction extends ServletBase
{
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        throw new RuntimeException( "This page does not support doGet" );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String firstName = request.getParameter( "firstName" );
        String lastName = request.getParameter( "lastName" );
        String agreementTypeId = request.getParameter( "agreementType" );
        String userName = request.getParameter( "userName" );
        String password = request.getParameter( "password" );

        if ( isValid( firstName, lastName, agreementTypeId, userName, password ) )
        {
            try
            {
                MemberDao memberDao = getMemberDao();
                String memberId = memberDao.createMember( firstName, lastName, agreementTypeId, userName, password );
                showValidValues( response, memberId, firstName, lastName, agreementTypeId );
            }
            catch (Exception exception)
            {
                showError( request, response, "Add member failed: " + exception.getMessage() );
            }
        }
        else
        {
            showError( request, response, "Missing required field" );
        }
    }

    private void showError( HttpServletRequest request, HttpServletResponse response, String message ) throws ServletException, IOException
    {
        // request.setAttribute( NewMember.MESSAGE, message );   // put on request scope hash map

        request.getSession().setAttribute( NewMember.MESSAGE, message );
        forward( request, response, "/newMember.html" );
    }

    private boolean isValid( String firstName, String lastName, String agreementTypeId, String userName, String password )
    {
        return !StringSupport.isEmptyString( firstName ) &&
               !StringSupport.isEmptyString( lastName ) &&
               !StringSupport.isEmptyString( userName ) &&
               !StringSupport.isEmptyString( password ) &&
               StringSupport.isGuid( agreementTypeId ) ;
    }

    private void showValidValues( HttpServletResponse response, String memberId,
                                  String firstName, String lastName, String agreementTypeId ) throws IOException
    {
        response.setContentType( "text/html" );

        PrintWriter out = response.getWriter();

        startHtml( out, "New Member Action" );
        endHeadStartBody( out, "Member Added" );
        out.println( "<h1>Added New Member</h1><table> " );
        addNameValue( out, "First Name", firstName );
        addNameValue( out, "Last Name", lastName );
        addNameValue( out, "ID", memberId );
        addNameValue( out, "AgreementTypeId", agreementTypeId );
        out.println( "</table>" );
        addBreaks( out, 2 );
        addMenuLink( out );
        addBreaks( out, 1 );
        addNewMemberLink( out );
        addBreaks( out, 1 );
        addShowMembersLink( out );
        endHtml( out );

        out.close();
    }

    private void addNameValue( PrintWriter out, String name, String value )
    {
        out.println( "<tr><td>" + name + "</td><td>" + value + "</td></tr> " );
    }
}
