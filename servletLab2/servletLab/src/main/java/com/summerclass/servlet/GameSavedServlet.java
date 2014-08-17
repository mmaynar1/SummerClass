package com.summerclass.servlet;

import com.summerclass.domain.GameState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameSavedServlet extends ServletBase
{
   public static final String URL = "/jsp/savedGame.jsp";

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( isLoggedIn( request, response ) )
        {
            GameState gameState = new GameState( request.getParameter( "boardSize" ),
                                                 request.getParameter( "gameScore" ),
                                                 request.getParameter( "boardAsString" ));

            request.getSession().setAttribute( "gameState", gameState );

            forward( request, response, URL );
        }
        else
        {
            redirectToLoginPage( request, response );
        }
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( isLoggedIn( request, response ) )
        {
            forward( request, response, URL );
        }
        else
        {
            redirectToLoginPage( request, response );
        }
    }
}
