package com.summerclass.integrationtest;

import com.summerclass.fractions.Fraction;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FractionTest extends SpringIntegrationTest
{
    @Test
    public void testAdd()
    {
        testAdd( new Fraction( 3, 4 ), new Fraction( 3, 2 ), new Fraction( 9, 4 ) );
        testAdd( new Fraction( -4, 5 ), new Fraction( 0 ), new Fraction( -4, 5 ) );
        testAdd( new Fraction( -4, 5 ), new Fraction( -3, 6 ), new Fraction( -13, 10 ) );
        testAdd( new Fraction( 0 ), new Fraction( 0, 34 ), new Fraction( 0 ) );
        testAdd( new Fraction( 0 ), new Fraction( 0, 34 ), new Fraction( 0, 23 ) );
        testAdd( new Fraction( 0, -45 ), new Fraction( 0, 34 ), new Fraction( 0 ) );
        testAdd( new Fraction( 15, 3 ), new Fraction( 6, 7 ), new Fraction( 41, 7 ) );
    }

    private void testAdd( Fraction fraction1, Fraction fraction2, Fraction result )
    {
        assertTrue( fraction1.add( fraction2 ).isEqual( result ) );
    }

    @Test
    public void testEquals()
    {
        assertTrue( new Fraction( 3, 4 ).isEqual( new Fraction( 3, 4 ) ) );
        assertTrue( new Fraction( 0 ).isEqual( new Fraction( 0, 23 ) ) );
        assertTrue( new Fraction( -1, -17 ).isEqual( new Fraction( -1, -17 ) ) );
        assertTrue( new Fraction( -1 ).isEqual( new Fraction( -1, 1 ) ) );
        assertTrue( new Fraction( -5, 10 ).isEqual( new Fraction( 1, -2 ) ) );
        assertTrue( new Fraction( -1 ).isEqual( new Fraction( 1, -1 ) ) );
        assertTrue( new Fraction( -3, 4 ).isEqual( new Fraction( 3, -4 ) ) );
        assertTrue( new Fraction( 0, 55 ).isEqual( new Fraction( 0, -2 ) ) );
        assertTrue( new Fraction( 33, 57 ).isEqual( new Fraction( -33, -57 ) ) );
        assertFalse( new Fraction( 1, 2 ).isEqual( new Fraction( 3, 5 ) ) );
        assertFalse( new Fraction( -1, -3 ).isEqual( new Fraction( -1, 3 ) ) );
    }

    @Test
    public void testSubtract()
    {

        testSubtract( new Fraction( 3, 4 ), new Fraction( 3, 2 ), new Fraction( -3, 4 ) );
        testSubtract( new Fraction( -5, 4 ), new Fraction( 0 ), new Fraction( -5, 4 ) );
        testSubtract( new Fraction( -1, 3 ), new Fraction( -1, 3 ), new Fraction( 0 ) );
    }

    private void testSubtract( Fraction fraction1, Fraction fraction2, Fraction result )
    {
        assertTrue( fraction1.subtract( fraction2 ).isEqual( result ) );
    }

    @Test
    public void testMultiplication()
    {
        Fraction fraction1 = new Fraction( 3, 4 );
        Fraction fraction2 = new Fraction( 3, 2 );

        Fraction result = new Fraction( 9, 8 );
        assertTrue( fraction1.multiply( fraction2 ).isEqual( result ) );

        Fraction wrongResult = new Fraction( 9, 7 );
        assertFalse( fraction1.multiply( fraction2 ).isEqual( wrongResult ) );

        fraction1 = new Fraction( -5, 8 );
        fraction2 = new Fraction( 6, -4 );
        result = new Fraction( 30, 32 );
        assertTrue( fraction1.multiply( fraction2 ).isEqual( result ) );
    }

    @Test
    public void testDivision()
    {
        Fraction fraction1 = new Fraction( 3, 4 );
        Fraction fraction2 = new Fraction( 3, 2 );

        Fraction result = new Fraction( 1, 2 );
        assertTrue( fraction1.divide( fraction2 ).isEqual( result ) );

        Fraction wrongResult = new Fraction( 2, 3 );
        assertFalse( fraction1.divide( fraction2 ).isEqual( wrongResult ) );
    }

    @Test
    public void testDivideByZero()
    {
        boolean worked = false;
        try
        {
            new Fraction( 3, 4 ).divide( new Fraction( 0 ) );

        }
        catch (RuntimeException exception)
        {
            worked = true;
        }

        assertTrue( worked );
    }

    @Test
    public void testInvert()
    {
        Fraction fraction = new Fraction( 3, 4 );
        Fraction inverse = new Fraction( 4, 3 );
        assertTrue( fraction.invert().isEqual( inverse ) );

        fraction = new Fraction( 1 );
        inverse = new Fraction( 1 );
        assertTrue( fraction.invert().isEqual( inverse ) );

        fraction = new Fraction( -1, 3 );
        inverse = new Fraction( 3, -1 );
        assertTrue( fraction.invert().isEqual( inverse ) );

        fraction = new Fraction( -1, 3 );
        inverse = new Fraction( -3, 1 );
        assertTrue( fraction.invert().isEqual( inverse ) );

        fraction = new Fraction( 0 );
        inverse = new Fraction( 0, 56 );
        assertTrue( fraction.invert().isEqual( inverse ) );

        Fraction wrongResult = new Fraction( 2, 3 );
        assertFalse( fraction.invert().isEqual( wrongResult ) );
    }
}