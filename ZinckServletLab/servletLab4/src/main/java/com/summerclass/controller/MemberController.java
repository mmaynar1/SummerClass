package com.summerclass.controller;

import com.summerclass.domain.Member;
import com.summerclass.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.summerclass.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class MemberController
{
    public static final String ADD_MEMBER_URL = "/addMember";
    public static final String VIEW_MEMBERS_URL = "/viewMembers";
    public static final String EDIT_MEMBER_URL = "/editMember";
    public static final String ERROR_URL = "jsp/error.jsp";

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView startIndex()
    {
        return new ModelAndView();
    }

    @RequestMapping(value = ADD_MEMBER_URL, method = RequestMethod.GET)
    public ModelAndView startAddMember( HttpServletRequest request )
    {
        ModelAndView modelAndView;

        try
        {
            modelAndView = new ModelAndView( "jsp/addMember.jsp" );
            modelAndView.addObject( "member", new Member() );
            modelAndView.addObject( "agreementTypes", memberService.getAllAgreementTypesIdName() );

        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }


        return modelAndView;
    }


    @RequestMapping(value = ADD_MEMBER_URL, method = RequestMethod.POST)
    public ModelAndView addMember( @ModelAttribute("member") Member member, HttpServletRequest request )
    {
        ModelAndView modelAndView;

        Result result;
        modelAndView = new ModelAndView( "jsp/addMember.jsp" );
        modelAndView.addObject( "agreementTypes", memberService.getAllAgreementTypesIdName() );
        try
        {
            result = memberService.addMember( member );
            member.clear();

            modelAndView.addObject( "result", result );

        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }


        return modelAndView;
    }

    @RequestMapping(value = VIEW_MEMBERS_URL, method = RequestMethod.GET)
    public ModelAndView startViewMembers( HttpServletRequest request )
    {
        ModelAndView modelAndView;

        try
        {
            modelAndView = new ModelAndView( "jsp/viewMembers.jsp" );
            modelAndView.addObject( "member", new Member() );
            modelAndView.addObject( "agreementTypes", memberService.getAllAgreementTypesIdName() );

        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }

        return modelAndView;
    }

    @RequestMapping(value = VIEW_MEMBERS_URL, method = RequestMethod.POST)
    public ModelAndView viewMembers( @ModelAttribute("member") Member member, HttpServletRequest request )
    {
        ModelAndView modelAndView;

        try
        {
            modelAndView = new ModelAndView( "jsp/viewMembers.jsp" );
            modelAndView.addObject( "members", memberService.getFilteredMembers( member ) );
            modelAndView.addObject( "agreementTypes", memberService.getAllAgreementTypesIdName() );

        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }


        return modelAndView;
    }

    @RequestMapping(value = EDIT_MEMBER_URL, method = RequestMethod.GET)
    public ModelAndView startEditMember( @RequestParam String memberId, HttpServletRequest request )
    {
        ModelAndView modelAndView;

        try
        {
            Member member = memberService.getMember( memberId );
            modelAndView = new ModelAndView( "/jsp/editMember.jsp" );
            modelAndView.addObject( "member", member );
            modelAndView.addObject( "agreementTypes", memberService.getAllAgreementTypesIdName() );

        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }

        return modelAndView;
    }

    @RequestMapping(value = EDIT_MEMBER_URL, method = RequestMethod.POST)
    public ModelAndView editMember( @ModelAttribute("member") Member member, HttpServletRequest request )
    {
        ModelAndView modelAndView;

        Result result;
        modelAndView = new ModelAndView( "jsp/editMember.jsp" );
        try
        {
            result = memberService.updateMember( member );

            modelAndView.addObject( "agreementTypes", memberService.getAllAgreementTypesIdName() );
            modelAndView.addObject( "result", result );

        }
        catch (Exception exception)
        {
            return new ModelAndView( ERROR_URL );
        }


        return modelAndView;
    }

    @RequestMapping(value = "/ajax/memberEventsCount", method = RequestMethod.POST)
    public
    @ResponseBody
    int getMemberEventsCount( @RequestParam String memberId)
    {
       return memberService.getEventsCount(memberId);
    }

}
