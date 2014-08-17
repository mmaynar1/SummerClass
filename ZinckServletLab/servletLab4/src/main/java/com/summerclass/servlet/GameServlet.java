package com.summerclass.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class GameServlet extends ServletBase
{
    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        try
        {
            if ( !validateSession( request ) )
            {
                forward( request, response, LOGIN_URL );
            }
            else
            {
                storeGame( request );

                loadNextPage( request, response );
            }
        }
        catch (Exception exception)
        {
            forwardError( request, response );
        }
    }

    private void loadNextPage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( request.getParameter( "submitType" ).equals( Keywords2048.quit.getText() ) )
        {
            forwardMainPage( request, response );
        }
        else if ( request.getParameter( "submitType" ).equals( Keywords2048.save.getText() ) )
        {
            forwardGamePage( request, response );
        }
        else
        {
            forwardError( request, response );
        }
    }

    private void storeGame( HttpServletRequest request )
    {
        final String javascriptBoardArray = getJavascriptString( request );
        request.getSession().setAttribute( "board", javascriptBoardArray );

        final String score = request.getParameter( "score" );
        request.getSession().setAttribute( "score", score );
    }

    private String getJavascriptString( HttpServletRequest request )
    {
        String javascriptBoardArray = "[";
        final String DELIMETER = Keywords2048.delimiter.getText();

        for (int row = 0; row < 4; ++row)
        {
            javascriptBoardArray += "[";
            List<String> tileValues = new ArrayList<>();

            for (int column = 0; column < 4; ++column)
            {
                String name = Keywords2048.input.getText() + DELIMETER + row + DELIMETER + column;
                tileValues.add( request.getParameter( name ) );
            }
            String tileRow = org.springframework.util.StringUtils.collectionToDelimitedString( tileValues, "," );
            javascriptBoardArray += tileRow;
            javascriptBoardArray += "]";
            if ( row - 2 < 4 )
            {
                javascriptBoardArray += ",";
            }
        }
        javascriptBoardArray += "]";
        return javascriptBoardArray;
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( !validateSession( request ) )
        {
            forward( request, response, LOGIN_URL );
        }
        else
        {
            if ( request.getSession().getAttribute( "board" ) == null )
            {
                request.getSession().setAttribute( "board", "null" );
            }
            forwardGamePage( request, response );
        }
    }

    public void forwardGamePage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String url = "/jsp/game2048.jsp";
        forward( request, response, url );
    }

    public void forwardMainPage( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        String url = "/jsp/index.jsp";
        forward( request, response, url );
    }
}
