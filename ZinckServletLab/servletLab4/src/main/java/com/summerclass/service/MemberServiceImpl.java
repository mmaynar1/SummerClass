package com.summerclass.service;

import com.summerclass.domain.IdName;
import com.summerclass.domain.Member;
import com.summerclass.domain.Result;
import com.summerclass.repository.AgreementTypesDao;
import com.summerclass.repository.EventDao;
import com.summerclass.repository.MemberDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService
{
    @Autowired
    private MemberDao memberDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private AgreementTypesDao agreementTypesDao;

    @Override
    public Result addMember( Member member )
    {
        Result result;
        if ( StringSupport.isEmptyString( member.getFirstName() ) )
        {
            result = new Result( Result.Status.failure, "First Name is a required field" );
        }
        else if ( StringSupport.isEmptyString( member.getLastName() ) )
        {
            result = new Result( Result.Status.failure, "Last Name is a required field" );
        }
        else if ( StringSupport.isEmptyString( member.getUsername() ) )
        {
            result = new Result( Result.Status.failure, "Username is a required field" );
        }
        else if ( StringSupport.isEmptyString( member.getPassword() ) )
        {
            result = new Result( Result.Status.failure, "Password is a required field" );
        }
        else if ( memberDao.getMember( member.getUsername() ) != null )
        {
            result = new Result( Result.Status.failure, "Username is already in use" );
        }
        else
        {
            memberDao.createMember( member );
            String successMessage = "Added " + member.getFirstName() + " " + member.getLastName() + " with id=" + member.getMemberId();
            result = new Result( Result.Status.success, successMessage );
        }

        return result;
    }

    @Override
    public List<Member> getFilteredMembers( Member member )
    {
        return memberDao.getMembers( member );
    }

    @Override
    public List<IdName> getAllAgreementTypesIdName()
    {
        return (List)agreementTypesDao.getAllAgreementTypesIdName();
    }

    @Override
    public Member getMember( String memberId )
    {
        return memberDao.getMemberById( memberId );
    }

    @Override
    public Result updateMember( Member member )
    {
        Result result;
        try
        {
            memberDao.updateMember( member );
            result = new Result( Result.Status.success, "Member updated successfully" );
        }
        catch (Exception exception)
        {
            result = new Result( Result.Status.failure, "Failed to update" );
        }

        return result;
    }



    @Override
    public int getEventsCount( String memberId )
    {
        int count = 0;
        if(StringSupport.isGuid( memberId ))
        {
            count = memberDao.getEventsCount(memberId);
        }
        else
        {
            throw new RuntimeException( "Invalid memberId" );
        }

        return count;
    }

    @Override
    public int createEvent( String memberId, String employeeId, String clubId, String eventTypeId )
    {
        int rowsAdded;
        if(StringSupport.isGuid( memberId ) &&
           StringSupport.isGuid( employeeId ) &&
           StringSupport.isGuid( clubId ) &&
           StringSupport.isGuid( eventTypeId ) )
        {
            rowsAdded = eventDao.createEvent( memberId, employeeId, clubId, eventTypeId );
        }
        else
        {
            throw new RuntimeException( "Invalid id" );
        }

        return rowsAdded;
    }
}
