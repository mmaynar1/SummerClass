package com.summerclass.controller;

import com.summerclass.domain.EventType;
import com.summerclass.domain.Result;
import com.summerclass.service.EventTypeService;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Enumeration;

@Controller
public class EventTypeController
{
    @Autowired
    private EventTypeService eventTypeService;

    @RequestMapping(value = "/addEventType", method = RequestMethod.GET)
    public ModelAndView addEventType()
    {
        EventType eventType = new EventType();
        eventType.setEventName( "starting" );
        return new ModelAndView( "jsp/addEventType.jsp", "eventType", eventType );
    }
    @RequestMapping(value = "/addEventType", method = RequestMethod.POST)
    public ModelAndView addEventType( @ModelAttribute("eventType") EventType eventType,
                                      HttpServletRequest request,
                                      HttpSession session ) throws Exception
    {
        ModelAndView modelAndView;

        /*
        EventType eventTypeReflection = new EventType();
        Enumeration<String> names = request.getParameterNames();
        while ( names.hasMoreElements() )
        {
            String name = names.nextElement();
            String value = request.getParameter( name );

            Class[] arguments = { value.getClass() };
            String methodName = "set" + name.substring( 0, 1 ).toUpperCase() + name.substring( 1 );
            Method method = eventTypeReflection.getClass().getMethod( methodName, arguments );
            method.invoke( eventTypeReflection, value );
        }
        System.out.println( " equals = " + eventType.equals( eventTypeReflection ) );
        */


        try
        {
            String loggedIn = (String) session.getAttribute( "loggedIn" );
            if ( StringSupport.safeEqual( loggedIn, "1" ) )
            {
                Result result = eventTypeService.addEventType( eventType );
                eventType.setEventName( "" );
                modelAndView = new ModelAndView( "jsp/addEventType.jsp", "result", result );
            }
            else
            {
                // login page
                modelAndView = new ModelAndView( "forward:newMember.html" );
            }
        }
        catch (Exception exception)
        {
            // error page
            modelAndView = new ModelAndView( "forward:newMember.html" );
        }

        return modelAndView;
    }
}
