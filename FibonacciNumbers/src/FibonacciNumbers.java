public class FibonacciNumbers
{
    public static void main( String[] args )
    {
        FibonacciNumbers fibonacciNumbers = new FibonacciNumbers();
        int numberOfTerms = 20;
        fibonacciNumbers.printFibonacciNumbers( numberOfTerms );
    }

    public void printFibonacciNumbers( int numberOfTerms )
    {
        for (int i = 1; i < numberOfTerms; i++)
        {
            System.out.println( getFibonacciNumber( i ) );
        }
    }

    private int getFibonacciNumber( int term )
    {
        int result = 0;
        if ( term >= 0 )
        {
            if ( term == 0 )
            {
                result = 0;
            }
            else if ( term == 1 || term == 2 )
            {
                result = 1;
            }
            else
            {
                int parent = 1;
                int grandParent = 1;
                for (int i = 3; i <= term; i++)
                {
                    result = parent + grandParent;
                    grandParent = parent;
                    parent = result;
                }
            }
        }
        else
        {
            throw new RuntimeException( "Invalid term number" );
        }

        return result;
    }
}