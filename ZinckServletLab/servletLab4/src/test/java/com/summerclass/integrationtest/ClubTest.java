package com.summerclass.integrationtest;

import com.summerclass.domain.Club;
import com.summerclass.repository.ClubDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class ClubTest extends SpringIntegrationTest
{
    @Autowired
    private ClubDao clubDao;

    @Test
    public void getClubIdFailure()
    {
        final int clubNumber = 3700;
        final String clubId = clubDao.getClubId( clubNumber );
        assertNull( clubId );
    }

    @Test
    public void testGetCount()
    {
        assertTrue( clubDao.getClubCount() == 3 );
    }

    @Test
    public void testGetIdAndNumber()
    {
        final int clubNumber = 3000;
        final String clubId = clubDao.getClubId( clubNumber );
        assertTrue( StringSupport.isGuid( clubId ) );
        int foundClubNumber = clubDao.getClubNumber( clubId );
        assertEquals( clubNumber, foundClubNumber );
    }

    @Test
    public void testGetName()
    {
        final int clubNumber = 3000;
        final String clubId = clubDao.getClubId( clubNumber );

        String usingIdAndNumberName = clubDao.getClubName( clubId, clubNumber );
        assertNotNull( usingIdAndNumberName );

        String usingIdName = clubDao.getClubName( clubId );
        assertNotNull( usingIdName );

        String usingNumberName = clubDao.getClubName( clubNumber );
        assertNotNull( usingNumberName );

        assertEquals( usingIdAndNumberName, usingIdName );
        assertEquals( usingIdAndNumberName, usingNumberName );
    }

    @Test
    public void testGetNameSafe()
    {
        int clubNumber = 3001;
        String safeName = clubDao.getClubNameSafe( clubNumber );
        String name = clubDao.getClubName( clubNumber );
        assertEquals( safeName, name );

        clubNumber = 666;
        safeName = clubDao.getClubNameSafe( clubNumber );
        assertNull( safeName );

        boolean worked = false;
        try
        {
            name = clubDao.getClubName( clubNumber );
        }
        catch (EmptyResultDataAccessException exception)
        {
            worked = true;
        }
        assertTrue( worked );   // assert threw exception
    }

    @Test
    public void testGetClubNumbers()
    {
        List<Integer> clubNumbers = clubDao.getClubNumbers();

        List<Club> clubs = clubDao.getAll();

        assertEquals( clubNumbers.size(), clubs.size() );

        assertEquals( clubNumbers.size(), clubDao.getClubCount() );

        for (int i = 0; i < clubNumbers.size(); ++i)
        {
            assertTrue( clubNumbers.get( i ) == clubs.get( i ).getNumber() );
        }
    }

    @Test
    public void testGetIds()
    {
        List<String> ids = clubDao.getIds();

        List<Club> clubs = clubDao.getAll();

        assertEquals( ids.size(), clubs.size() );

        for (int i = 0; i < ids.size(); ++i)
        {
            assertTrue( StringSupport.isGuid( ids.get( i ) ) );

            assertTrue( StringSupport.isGuid( clubs.get( i ).getId() ) );

            assertEquals( ids.get( i ), clubs.get( i ).getId() );
        }
    }

    @Test
    public void testGetClub()
    {
        final int clubNumber = 3000;
        final String clubId = clubDao.getClubId( clubNumber );
        assertTrue( StringSupport.isGuid( clubId ) );

        Club club = clubDao.getClubUnsafe( clubId );

        assertEquals( clubNumber, club.getNumber() );
        assertEquals( clubId, club.getId() );
        assertNotNull( club.getName() );
    }

    @Test
    public void testGetClubSafe()
    {
        final int clubNumber = 3000;
        final String clubId = clubDao.getClubId( clubNumber );
        assertTrue( StringSupport.isGuid( clubId ) );

        Club unsafeClub = clubDao.getClubUnsafe( clubId );
        Club safeClub = clubDao.getClubSafe( clubId );
        assertTrue( unsafeClub.equals( safeClub ) );

        String invalidClubId = "Invalid" + clubId;
        safeClub = clubDao.getClubSafe( invalidClubId );
        assertNotNull( safeClub );
        assertNull( safeClub.getId() );
        assertFalse( safeClub.isValid() );

        boolean worked = false;
        try
        {
            unsafeClub = clubDao.getClubUnsafe( invalidClubId );
        }
        catch (EmptyResultDataAccessException exception)
        {
            worked = true;
        }
        assertTrue( worked );
    }

    @Test
    public void testGetClubs()
    {
        List<Club> clubs = clubDao.getAll();
        assertTrue( clubs.size() == 3 );

        for (Club club : clubs)
        {
            assertTrue( StringSupport.isGuid( club.getId() ) );
            assertNotNull( club.getName() );
            assertTrue( club.getNumber() > 0 );
        }
    }
}
