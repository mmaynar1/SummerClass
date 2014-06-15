public class MoonWeightConversion
{
    public static void main( String[] args )
    {
        MoonWeightConversion moonWeightConversion = new MoonWeightConversion();

        int earthWeightInPounds = 210;
        final String unit = " lbs";

        double moonWeight = moonWeightConversion.calculateMoonWeight( earthWeightInPounds );
        System.out.println( "Earth Weight: " + earthWeightInPounds + unit );
        System.out.println( "Moon Weight: " + moonWeight + unit );
    }

    private double calculateMoonWeight( int earthWeight )
    {
        double earthToMoonMultiplier = 0.17;
        return (earthWeight * earthToMoonMultiplier);
    }
}