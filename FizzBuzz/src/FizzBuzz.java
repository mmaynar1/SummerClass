public class FizzBuzz
{
    public static void main( String[] args )
    {
        FizzBuzz fizzBuzz = new FizzBuzz();

        int howMany = 100;
        fizzBuzz.printFizzBuzz( howMany );
    }

    private void printFizzBuzz(int howMany)
    {
        for (int number = 1; number <= howMany ; ++number)
        {
            if(number % 3 == 0)
            {
                System.out.print( "Fizz" );
            }
            if(number % 5 == 0)
            {
                System.out.print( "Buzz" );
            }
            if(number % 3 != 0 && number % 5 != 0)
            {
                System.out.print( number );
            }

            System.out.println();
        }
    }
}