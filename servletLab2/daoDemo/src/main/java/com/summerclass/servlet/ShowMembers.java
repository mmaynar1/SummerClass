package com.summerclass.servlet;

import com.summerclass.domain.Member;
import com.summerclass.utility.RandomGenerator;
import com.summerclass.utility.StringSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ShowMembers extends ServletBase
{
    public final static int MAX_NAME_SIZE = 50;
    public final static String MEMBER_NAME = "memberName";

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        showMembers( request, response );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        showMembers( request, response );
    }

    private void showMembers( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        String memberName = (String) request.getParameter( MEMBER_NAME );
        response.setContentType( "text/html" );

        PrintWriter out = response.getWriter();

        showMembers( out, memberName );

        out.close();
    }

    private void showMembers( PrintWriter out, String memberName )
    {
        startHtml( out, "Show Members" );
        out.println( "    <script type='text/javascript'> " +
                     "        function search() " +
                     "        { " +
                     "            debugger;\n " +
                     "            document.getElementById('form').submit();\n " +
                     "        } " +
                     "    </script> " );
        endHeadStartBody( out, "Show Members" );

        startForm( out, "/daoDemo-1.0-exploded/showMembers.html" );

        out.println( "<table>" );
        String button = "<input type='button' onclick='search()' value=' Search '/>";
        addInput( out, "Name:", MEMBER_NAME, Required.no, MAX_NAME_SIZE, button );
        out.println( "</table> " );
        endForm( out );

        addBreaks( out, 2 );
        List<Member> members = getMemberDao().getAll( memberName );
        out.println( "<div style='width: 700px; height: 200px; overflow: scroll; border: 1px solid darkgray;'>" );
        out.println( "<table width='100%' border='1' cellspacing='0' cellpadding='5px'>" );
        addTableHeader( out, "Member ID", "First Name", "Last Name", "Agreement Type" );
        for (Member member : members)
        {
            addTableRow( out, member.getMemberId(), member.getFirstName(), member.getLastName(), member.getAgreementType() );
        }
        out.println( "</table>" );
        out.println( "</div>" );
        addBreaks( out, 2 );

        addMenuLink( out );
        addBreaks( out, 1 );
        addNewMemberLink( out );
        endHtml( out );
    }

    private void addInput( PrintWriter out, String label, String name, Required required,
                           int length, String cell )
    {
        out.println( "<tr>" );
        out.println( "<td>" );
        if ( required == Required.yes )
        {
            out.println( "*" );
        }
        out.println( "</td>" );
        out.println( "<td><label for='" + name + "'>" + label + "</label></td>" );
        out.println( "<td><input type='text' name='" + name + "' id='" + name + "' maxLength='" + length + "'/></td>" );
        if ( !StringSupport.isEmptyString( cell ) )
        {
            out.println( "<td>" );
            out.println( cell );
            out.println( "</td>" );
        }
        out.println( "<tr>" );
    }
}
