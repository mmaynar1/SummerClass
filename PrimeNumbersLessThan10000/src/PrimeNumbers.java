public class PrimeNumbers
{
    public static void main( String[] args )
    {
        final int MAX_NUMBER = 10000;
        int largestPrime = 2;
        for (int number = 2; largestPrime < MAX_NUMBER; ++number)
        {
            boolean isPrime;
            if ( isNumberEven( number ) && number != 2 )
            {
                isPrime = false;
            }
            else
            {
                isPrime = isPrime( number );
            }
            if ( isPrime && number < MAX_NUMBER )
            {
                System.out.println( number );
                largestPrime = number;
            }
        }
    }

    private static boolean isPrime( int number )
    {
        boolean isPrime = true;
        for (int i = 2; i < (number / 2); i++)
        {
            if ( number % i == 0 )
            {
                isPrime = false;
            }
        }
        return isPrime;
    }

    private static boolean isNumberEven( int number )
    {
        return (number % 2 == 0);
    }
}