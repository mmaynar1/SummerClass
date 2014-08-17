package com.summerclass.controller;

import com.summerclass.utility.StringSupport;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object controller ) throws Exception
    {
        boolean keepGoing = true;

        boolean ajaxCall = StringSupport.safeEqual( request.getParameter( "ajax" ), "1" );
        if ( !isLoggedIn( request ) && !ajaxCall   )
        {
            response.sendRedirect( "login" );
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
        System.out.println( "Response: " + request.getRequestURI() );
    }


    private boolean isLoggedIn( HttpServletRequest request )
    {
        return request.getSession().getAttribute( "loginMember" ) != null;
    }

}
