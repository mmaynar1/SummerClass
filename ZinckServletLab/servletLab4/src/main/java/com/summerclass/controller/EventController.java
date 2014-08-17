package com.summerclass.controller;

import com.summerclass.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventController
{
    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/ajax/createEvent", method = RequestMethod.POST)
    public
    @ResponseBody
    int createEvent( @RequestParam String memberId,
                     @RequestParam String employee,
                     @RequestParam String club,
                     @RequestParam String eventType )
    {
        return memberService.createEvent( memberId, employee, club, eventType );
    }
}
