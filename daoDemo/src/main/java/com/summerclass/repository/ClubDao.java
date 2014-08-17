package com.summerclass.repository;

import com.summerclass.domain.Club;

import java.util.List;

public interface ClubDao
{
    String getClubId( int number );

    int getClubNumber( String clubId );

    String getClubName( int clubNumber );  // redundant for demo purpose

    String getClubNameSafe( int clubNumber );

    String getClubName( String clubId );

    String getClubName( String clubId, int clubNumber );

    int getClubCount();

    Club getClubSafe( String clubId );

    Club getClubUnsafe( String clubId );

    List<Club> getAll();

    List<Integer> getClubNumbers();

    List<String> getIds();

    Club getClub( int clubNumber );
}
