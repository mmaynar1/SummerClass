package com.summerclass.integrationtest;

import com.summerclass.domain.Club;
import com.summerclass.domain.Employee;
import com.summerclass.repository.ClubDao;
import com.summerclass.repository.EmployeeDao;
import com.summerclass.utility.StringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ClubTest extends SpringIntegrationTest
{
    @Autowired
    private ClubDao clubDao;

    @Test
    public void testGetClubs()
    {
        List<Club> clubs = clubDao.getClubs();
        assertEquals( clubs.size(), clubDao.getCount() );
        for (Club club : clubs)
        {
            assertTrue( StringSupport.isGuid( club.getId() ) );
        }
    }
}