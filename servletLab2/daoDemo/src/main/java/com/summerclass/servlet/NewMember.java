package com.summerclass.servlet;

import com.summerclass.domain.Selectable;
import com.summerclass.domain.Club;
import com.summerclass.utility.StringSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class NewMember extends ServletBase
{
    public final static String MESSAGE = "message";

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        collectInputs( request, response );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        collectInputs( request, response );
    }

    private void collectInputs( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/html" );

        PrintWriter out = response.getWriter();
        //String message = (String) request.getAttribute( MESSAGE );
        String message = (String) request.getSession().getAttribute( MESSAGE );
        request.getSession().removeAttribute( MESSAGE );

        collectInputs( out, message );

        out.close();
    }

    private void collectInputs( PrintWriter out, String message )
    {
        startHtml( out, "New Member" );
        out.println( "    <style type='text/css'> " +
                     "       .error" +
                     "       {" +
                     "          color: red; " +
                     "       }" +
                     "    </style> " +
                     "    <script type='text/javascript'> " +
                     "        $(document).ready( function() { $('#submitButton').bind( 'click', function() { $('#form').submit(); } ); } );" +
                     "    </script> " );
        endHeadStartBody( out, "Create New Member" );

        if ( !StringSupport.isEmptyString( message ) )
        {
            out.println( "<div class='error'>" + message + "</div>" );
        }


        startForm( out, "newMember.action" );
        out.println( "    <table> " +
                     "        <tr> " +
                     "            <td><span title='Required field'>*</span></td> " +
                     "            <td><label for='firstName'>First Name</label></td> " +
                     "            <td><input type='text' id='firstName' name='firstName' value='' maxlength='50'/></td> " +
                     "        </tr> " +
                     "        <tr> " +
                     "            <td><span title='Required field'>*</span></td> " +
                     "            <td><label for='firstName'>Last Name</label></td> " +
                     "            <td><input type='text' id='lastName' name='lastName' value='' maxlength='50'/></td> " +
                     "        </tr> " );

        List<Selectable> agreementTypes = getMemberDao().getAgreementTypes();
        addSelect( out, "Agreement Type:", "agreementType", agreementTypes );

        List<Club> clubs = getClubDao().getAll();
        addSelect( out, "Club:", "club", clubs );

        out.println( "        <tr> " +
                     "            <td></td> " +
                     "            <td></td> " +
                     "            <td><input id='submitButton' type='button' value=' Submit '/></td> " +
                     "        </tr> " +
                     "    </table> " +
                     "    <script>$(document).ready( function() { //alert( 'prove jQuery and common.js loaded ' + formatMoney( 12.3 ) ); } );</script>" );
        endForm( out );
        addMenuLink( out );
        addBreaks( out, 1 );
        addShowMembersLink( out );
        endHtml( out );
    }
}
