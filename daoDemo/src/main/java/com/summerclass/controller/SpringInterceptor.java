package com.summerclass.controller;

import com.summerclass.domain.LoggedInMember;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// todo update SpringConfiguration to add interceptor

@Component
public class SpringInterceptor extends HandlerInterceptorAdapter
{
    @Autowired
    private LoggedInMember loggedInMember;

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object controller ) throws Exception
    {
        boolean keepGoing;

        // todo remove this print statement
        System.out.println( "Request: " + request.getRequestURI() );

        boolean ajaxCall = StringSupport.safeEqual( request.getParameter( "ajax" ), "1" );
        boolean loggingIn = request.getRequestURL().toString().endsWith( "login.spr" );
        boolean alreadyLoggedIn = loggedInMember.isLoggedIn();

        if ( alreadyLoggedIn || loggingIn || ajaxCall )
        {
            keepGoing = true;
        }
        else
        {
            response.sendRedirect( "index.html" );
            keepGoing = false;
        }

        return keepGoing;
    }

    @Override
    public void postHandle( HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView mav ) throws java.lang.Exception
    {
        // todo remove this print statement
        System.out.println( "Response: " + request.getRequestURI() );
    }
}
