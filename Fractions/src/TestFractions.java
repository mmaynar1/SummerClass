public class TestFractions
{
    public static void main( String[] args )
    {
        Fractions fraction1 = new Fractions( 3, 4 );
        Fractions fraction2 = new Fractions( 3, 2 );

        System.out.println( "Fraction 1: " + fraction1.toString() );
        System.out.println( "Fraction 2: " + fraction2.toString() );

        System.out.println();

        System.out.println( "Addition:" );
        System.out.println( fraction1.add( fraction2 ) );
        System.out.println( fraction1.add( fraction2 ).getDecimalValue() );

        System.out.println();

        System.out.println( "Subtraction:" );
        System.out.println( fraction1.subtract( fraction2 ) );
        System.out.println( fraction1.subtract( fraction2 ).getDecimalValue() );

        System.out.println();

        System.out.println( "Multiplication:" );
        System.out.println( fraction1.multiply( fraction2 ) );
        System.out.println( fraction1.multiply( fraction2 ).getDecimalValue() );

        System.out.println();

        System.out.println( "Division:" );
        System.out.println( fraction1.divide( fraction2 ) );
        System.out.println( fraction1.divide( fraction2 ).getDecimalValue() );

        System.out.println();

        System.out.println( "Invert:" );
        System.out.println( "Fraction1: " + fraction1.invert().getDecimalValue() + " , " + fraction1.invert().toString() );
        System.out.println( "Fraction2: " + fraction2.invert().getDecimalValue() + " , " + fraction2.invert().toString() );
    }
}
