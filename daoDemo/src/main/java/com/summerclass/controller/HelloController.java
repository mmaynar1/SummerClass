package com.summerclass.controller;

import com.summerclass.domain.Company;
import com.summerclass.domain.EventType;
import com.summerclass.domain.IdName;
import com.summerclass.domain.Member;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController
{
    private Logger logger = Logger.getLogger( getClass() );

    @Autowired
    private Company company;

    @RequestMapping(value = "/hi", method = RequestMethod.POST)
    public ModelAndView hi( @RequestParam String firstName,
                            @RequestParam String lastName )
    {
        System.out.println( company.getName() );

        ModelAndView modelAndView;
        if ( firstName.equalsIgnoreCase( "bob" ) )
        {
            modelAndView = getHiModelAndView();
        }
        else
        {
            modelAndView = getGoodByeModelAndView();
        }

        return modelAndView;
    }

    private ModelAndView getHiModelAndView()
    {
        return new ModelAndView( "jsp/hi.jsp", "bean", new IdName( "it", "worked" ) );
    }

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public ModelAndView hi()
    {
        IdName idName = new IdName( "someId", "someName" );
        ModelAndView modelAndView = new ModelAndView( "jsp/hi.jsp" );
        modelAndView.addObject( "bean", idName );
        IdName total = new IdName( "total", "$100.00" );
        modelAndView.addObject( "total", total );
        return modelAndView;
    }

    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    public ModelAndView goodBye()
    {
        return getGoodByeModelAndView();
    }

    private ModelAndView getGoodByeModelAndView()
    {
        Member member = new Member();
        member.setLastName( "Good" );
        member.setFirstName( "Bye" );

        EventType eventType = new EventType();
        eventType.setEventName( "So Long" );

        ModelAndView modelAndView = new ModelAndView( "jsp/hello.jsp" );
        modelAndView.addObject( "member", member );
        modelAndView.addObject( "et", eventType );
        return modelAndView;
    }

    @RequestMapping(value = "/helloUrl", method = RequestMethod.GET)
    public ModelAndView helloMethod()
    {
        Member member = new Member();
        member.setLastName( "Smith" );
        member.setFirstName( "John" );

        EventType eventType = new EventType();
        eventType.setEventName( "Some event type name" );

        ModelAndView modelAndView = new ModelAndView( "jsp/hello.jsp" );
        modelAndView.addObject( "member", member );
        modelAndView.addObject( "et", eventType );
        return modelAndView;
    }
}
