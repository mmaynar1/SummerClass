package com.summerclass.rpc;

import com.summerclass.common.StaticSpringApplicationContext;
import com.summerclass.domain.Club;
import com.summerclass.domain.IdName;
import com.summerclass.repository.ClubDao;

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
        Club club = clubDao.getClub( new Integer( idName.getId() ) );
        return club;
    }
}
