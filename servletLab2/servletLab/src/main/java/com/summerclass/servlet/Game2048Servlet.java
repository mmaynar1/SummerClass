package com.summerclass.servlet;

import com.summerclass.domain.GameState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Game2048Servlet extends ServletBase
{
    public static final String URL = "/jsp/game2048.jsp";

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        goPlay( request, response );
    }


    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        goPlay( request, response );
    }

    private void goPlay( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if ( isLoggedIn( request, response ) )
        {
            GameState gameState = (GameState) request.getSession().getAttribute( "gameState" );

            if (gameState == null )
            {
                gameState = new GameState( null, null, null );
            }
            else
            {
                //Keep the values within gameState
            }

            request.getSession().setAttribute( "gameState", gameState );
            forward( request, response, URL );
        }
        else
        {
            redirectToLoginPage( request, response );
        }
    }

}