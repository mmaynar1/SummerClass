package utility;

import java.math.BigDecimal;
import java.util.UUID;

public class RandomGenerator
{
    private static java.util.Random random = new java.util.Random();

    public static int getInt( int min, int max )
    {
        return (min + random.nextInt( max - min ));
    }

    public static String getGuid()
    {
        return UUID.randomUUID().toString().replace( "-", "" );
    }

    public static char getAlpha()
    {
        int value = getInt( 'A', 'Z' );
        return (char) value;
    }

    public static String getName()
    {
        int length = getInt( 3, 10 );
        String result = getString( length );
        result = result.substring( 0, 1 ).toUpperCase() + result.substring( 1 ).toLowerCase();
        return result;
    }

    public static BigDecimal getMoney()
    {
        return new BigDecimal( getInt( 10, 500 ) );
    }

    public static String getString( int length )
    {
        String result = "";
        for (int i = 0; i < length; ++i)
        {
            result += getAlpha();
        }
        return result;
    }
}