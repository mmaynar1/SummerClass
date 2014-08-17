package com.summerclass.rpc;

import com.summerclass.common.StaticSpringApplicationContext;
import com.summerclass.domain.Club;
import com.summerclass.domain.EventSession;
import com.summerclass.domain.IdName;
import com.summerclass.repository.ClubDao;
import com.summerclass.repository.EventDao;
import com.summerclass.utility.StringSupport;

import java.util.List;

public class MemberRpc
{
    public String logLine( String line )
    {
        System.out.println( line );
        return "printed";
    }

    public Club getClub( IdName idName )
    {
        ClubDao clubDao = StaticSpringApplicationContext.getSpringObject( ClubDao.class );
        Club club = clubDao.getClubSafe( idName.getId() );
        return club;
    }

    public List<EventSession> getPendingEvents(String memberId)
    {
        List<EventSession> events;
        if( StringSupport.isGuid( memberId ))
        {
            EventDao eventDao = StaticSpringApplicationContext.getSpringObject( EventDao.class );
            events = eventDao.getPendingMemberEvents( memberId );
        }
        else
        {
            throw new RuntimeException( "Invalid memberId" );
        }

        return events;
    }
}
