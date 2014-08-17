package com.summerclass.integrationtest;

import com.summerclass.repository.ClubDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ClubTest extends SpringIntegrationTest
{
    @Autowired
    private ClubDao clubDao;

    @Test
    public void myFirstTest()
    {
        logger.info( "Club id for 3000 is " + clubDao.getClubId( 3000 ) );
        String id = clubDao.getClubId( 3000 );
    }

    @Test
    public void testGetClubId()
    {
        final int clubNumber = 3000;
        String id = clubDao.getClubId( clubNumber );
        assertFalse( id.equals( "adsf" ) );
        assertEquals( id.length(), 32 );
    }

    @Test
    public void testClubCount()
    {
        assertTrue( clubDao.getClubCount() > 0 );
    }
}
