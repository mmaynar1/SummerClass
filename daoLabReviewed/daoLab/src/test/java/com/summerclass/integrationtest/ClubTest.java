package com.summerclass.integrationtest;

import com.summerclass.repository.ClubDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ClubTest extends SpringIntegrationTest
{
    @Autowired
    private ClubDao clubDao;

    @Test
    public void testGetClubId()
    {
        final int clubNumber = 3000;
        final String clubId = clubDao.getClubId( clubNumber );
        assertTrue( StringSupport.isGuid( clubId ) );
    }

    @Test
    public void testClubCount()
    {
        assertTrue( clubDao.getClubCount() > 0 );
    }
}