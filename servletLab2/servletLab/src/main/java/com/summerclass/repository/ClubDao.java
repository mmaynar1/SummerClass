package com.summerclass.repository;

import com.summerclass.domain.Club;

import java.util.List;

public interface ClubDao
{
    List<Club> getClubs();

    String getClubId( int number );

    int getCount();
}
