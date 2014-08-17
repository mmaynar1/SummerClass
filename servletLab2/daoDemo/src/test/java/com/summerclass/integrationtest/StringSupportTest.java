package com.summerclass.integrationtest;

import com.summerclass.utility.RandomGenerator;
import com.summerclass.utility.StringSupport;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class StringSupportTest extends SpringIntegrationTest
{
    @BeforeClass
    public void beforeClass()
    {
        logger.error( "before class" );
    }

    @AfterClass
    public void afterClass()
    {
        logger.error( "after class" );
    }

    @BeforeMethod
    public void beforeMethod( Method method )
    {
        logger.error( "before method" );
    }

    @AfterMethod
    public void afterMethod( ITestResult result )
    {
        logger.error( "after method" );
    }

    @Test
    public void testPositiveGuid()
    {
        logger.error( "testPositiveGuid" );
        assertTrue( StringSupport.isGuid( RandomGenerator.getGuid() ) );
        assertTrue( StringSupport.isGuid( "12345678901234567890123456789012" ) );
        assertTrue( StringSupport.isGuid( "12345f78901234567890123456789012" ) );
        assertTrue( StringSupport.isGuid( "12345678b01234567890123456789012" ) );
        assertTrue( StringSupport.isGuid( "1234567890123C567890123456789012" ) );
        assertTrue( StringSupport.isGuid( "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" ) );
    }

    @Test
    public void testNegativeGuid()
    {
        logger.error( "testNegativeGuid" );
        assertFalse( StringSupport.isGuid( null ) );
        assertFalse( StringSupport.isGuid( "" ) );
        assertFalse( StringSupport.isGuid( "1234567890123456789012345678901" ) );
        assertFalse( StringSupport.isGuid( "a" + "12345f78901234567890123456789012" ) );
    }

    @Test
    public void testAssertions()
    {
        logger.error( "testAssertions" );
        assertNotNull( "abc" );
        assertNull( null );
        assertTrue( 67 > 9 );
        assertTrue( "zzz".compareTo( "aaa" ) > 0 );

        String one = "ABC";
        String two = "ABC";
        assertEquals( one, two );

        List<String> values = getString();
        assertNotNull( values );
        assertTrue( values.size() == 2 );
    }

    private List<String> getString()
    {
        List<String> values = new ArrayList<>();
        values.add( "abc" );
        values.add( "zys" );
        return values;
    }

}
