package com.summerclass.fractions;

public class Fraction
{
    public static final String DIVIDE_BY_ZERO_ERROR = "Divided by ZERO";
    private int numerator;
    private int denominator;

    public Fraction( int numerator, int denominator )
    {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction( int wholeNumber )
    {
        this( wholeNumber, 1 );
    }

    public boolean isEqual( Fraction fraction2 )
    {
        boolean isEqual = false;
        Fraction fraction1 = new Fraction( this.getNumerator(), this.getDenominator() );
        fraction1.simplify();
        fraction2.simplify();

        if ( fraction1.isNegative() && fraction2.isNegative() )
        {
            if ( fraction1.isNumeratorNegative() && fraction2.isDenominatorNegative() )
            {
                fraction1.setNumerator( fraction1.getNumerator() * -1 );
                fraction2.setDenominator( fraction2.getDenominator() * -1 );
            }
            else if ( fraction1.isDenominatorNegative() && fraction2.isNumeratorNegative() )
            {
                fraction1.setDenominator( fraction1.getDenominator() * -1 );
                fraction2.setNumerator( fraction2.getNumerator() * -1 );
            }
        }

        if ( (fraction1.getNumerator() == fraction2.getNumerator() && fraction1.getDenominator() == fraction2.getDenominator()) )
        {
            isEqual = true;
        }
        return isEqual;
    }

    private boolean isNegative()
    {
        return (this.isNumeratorNegative() || this.isDenominatorNegative());
    }

    private boolean isNumeratorNegative()
    {
        return this.getNumerator() < 0;
    }

    private boolean isDenominatorNegative()
    {
        return this.getDenominator() < 0;
    }

    public Fraction add( Fraction fraction )
    {
        int sumDenominator = this.getDenominator() * fraction.getDenominator();
        int sumNumerator = (this.getNumerator() * fraction.getDenominator()) + (fraction.getNumerator() * this.getDenominator());

        Fraction sumFraction = new Fraction( sumNumerator, sumDenominator );
        return sumFraction.simplify();
    }

    public Fraction subtract( Fraction fraction )
    {
        int differenceDenominator = this.getDenominator() * fraction.getDenominator();
        int differenceNumerator = (this.getNumerator() * fraction.getDenominator()) - (fraction.getNumerator() * this.getDenominator());

        Fraction differenceFraction = new Fraction( differenceNumerator, differenceDenominator );
        return differenceFraction.simplify();

    }

    public Fraction multiply( Fraction fraction )
    {
        fraction.simplify();
        boolean isDivideByZero = fraction.isEqual( new Fraction( 0 ) );
        if ( !isDivideByZero )
        {
            Fraction productFraction = new Fraction( this.getNumerator() * fraction.getNumerator(), this.getDenominator() * fraction.getDenominator() );
            return productFraction.simplify();
        }
        else
        {
            throw new RuntimeException( DIVIDE_BY_ZERO_ERROR );
        }
    }

    public Fraction divide( Fraction fraction )
    {
        Fraction quotient = fraction.invert();
        return multiply( quotient );
    }

    public Fraction invert()
    {
        Fraction fraction;
        if ( this.getNumerator() != 0 )
        {
            int numerator = getDenominator();
            int denominator = getNumerator();
            fraction = new Fraction( numerator, denominator );
        }
        else
        {
            fraction = new Fraction( 0 );
        }

        return fraction;
    }

    public double getDecimalValue()
    {
        return ((double) getNumerator() / (double) getDenominator());
    }

    public String toString()
    {
        simplify();
        int quotient = getNumerator() / getDenominator();
        int remainder = getNumerator() % getDenominator();

        String displayString;
        if ( remainder == 0 )
        {
            displayString = quotient + "";
        }
        else if ( quotient != 0 )
        {
            displayString = quotient + " & " + remainder + " / " + getDenominator();
        }
        else
        {
            displayString = getNumerator() + " / " + getDenominator();
        }

        return displayString;
    }

    private Fraction simplify()
    {
        if ( this.getNumerator() != 0 )
        {
            int divisor = greatestCommonDivisor( getNumerator(), getDenominator() );
            setNumerator( getNumerator() / divisor );
            setDenominator( getDenominator() / divisor );
            if ( this.isNumeratorNegative() && this.isDenominatorNegative() )
            {
                setNumerator( Math.abs( getNumerator() ) );
                setDenominator( Math.abs( getDenominator() ) );
            }
        }
        else
        {
            this.setNumerator( 0 );
            this.setDenominator( 1 );
        }
        return this;
    }


    private int greatestCommonDivisor( int firstNumber, int secondNumber )
    {
        int largerNumber = Math.max( firstNumber, secondNumber );
        int smallerNumber = Math.min( firstNumber, secondNumber );

        int divisor;
        if ( smallerNumber != 0 )
        {
            divisor = largerNumber % smallerNumber;
        }
        else
        {
            throw new RuntimeException( DIVIDE_BY_ZERO_ERROR );
        }

        while ( divisor != 0 )
        {
            largerNumber = smallerNumber;
            smallerNumber = divisor;
            divisor = largerNumber % smallerNumber;
        }

        divisor = smallerNumber;
        return Math.abs( divisor );
    }

    private int getNumerator()
    {
        return numerator;
    }

    private int getDenominator()
    {
        return denominator;
    }

    private void setNumerator( int numerator )
    {
        this.numerator = numerator;
    }

    private void setDenominator( int denominator )
    {
        this.denominator = denominator;
    }
}
