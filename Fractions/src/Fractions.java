public class Fractions
{
    private int numerator;
    private int denominator;

    Fractions( int numerator, int denominator )
    {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    Fractions( int wholeNumber )
    {
        this( wholeNumber, 1 );
    }

    public Fractions add( Fractions fraction )
    {
        int sumDenominator = this.getDenominator() * fraction.getDenominator();
        int sumNumerator = (this.getNumerator() * fraction.getDenominator()) + (fraction.getNumerator() * this.getDenominator());

        Fractions sumFraction = new Fractions( sumNumerator, sumDenominator );
        return sumFraction.simplify();
    }

    public Fractions subtract( Fractions fraction )
    {
        int differenceDenominator = this.getDenominator() * fraction.getDenominator();
        int differenceNumerator = (this.getNumerator() * fraction.getDenominator()) - (fraction.getNumerator() * this.getDenominator());

        Fractions differenceFraction = new Fractions( differenceNumerator, differenceDenominator );
        return differenceFraction.simplify();

    }

    public Fractions multiply( Fractions fraction )
    {
        Fractions productFraction = new Fractions( this.getNumerator() * fraction.getNumerator(), this.getDenominator() * fraction.getDenominator() );
        return productFraction.simplify();
    }

    public Fractions divide( Fractions fraction )
    {
        Fractions quotient = fraction.invert();
        return multiply( quotient );
    }

    public Fractions invert()
    {
        int numerator = getDenominator();
        int denominator = getNumerator();
        return new Fractions( numerator, denominator );
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

    private Fractions simplify()
    {
        int divisor = greatestCommonDivisor( getNumerator(), getDenominator() );
        setNumerator( getNumerator() / divisor );
        setDenominator( getDenominator() / divisor );
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
            throw new RuntimeException( "Divided by ZERO" );
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
