public class InchesToMetersTable
{
    public static void main( String[] args )
    {
        InchesToMetersTable table = new InchesToMetersTable();
        int feet = 12;
        table.printInchesToMetersTable( feet );
    }

    private double inchesToMeters( double inches )
    {
        double inchesToMetersDivisor = 39.37;
        return (inches / inchesToMetersDivisor);
    }

    private double feetToInches( double feet )
    {
        int inchesPerFoot = 12;
        return (feet * inchesPerFoot);
    }

    private void printInchesToMetersTable( int feet )
    {
        int numberOfInchesBeforeBreak = 12;

        int counter = 0;
        for (int inch = 1; inch <= feetToInches( feet ); ++inch)
        {
            System.out.println( inch + " inches is " + inchesToMeters( inch ) + " meters" );

            ++counter;
            if(counter == numberOfInchesBeforeBreak)
            {
                System.out.println();
                counter = 0;
            }
        }
    }
}