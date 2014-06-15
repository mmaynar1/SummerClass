public class TimesTable
{
    public static final String DIVIDER = "|";
    public static final int COLUMN_WIDTH = 6;

    public static void main( String[] args )
    {
        TimesTable timesTable = new TimesTable();
        final int MAX_NUMBER = 12;
        timesTable.printHeader( MAX_NUMBER );
        timesTable.printTimesTable( MAX_NUMBER );
    }

    public void printTimesTable( int number )
    {
        for (int row = 1; row <= number; row++)
        {
            for (int column = 1; column <= number; column++)
            {
                if ( column == 1 )
                {
                    printFormatted( row + "", COLUMN_WIDTH );
                    printFormatted( DIVIDER, COLUMN_WIDTH );
                }
                printFormatted( (row * column) + "", COLUMN_WIDTH );
            }
            System.out.println();
        }
    }

    public void printHeader( int number )
    {
        printNumberHeader( number );
        printHorizontalRule( number );
    }

    private void printNumberHeader( int number )
    {
        printFormatted( "*", COLUMN_WIDTH );
        printFormatted( DIVIDER, COLUMN_WIDTH );
        for (int i = 1; i <= number; i++)
        {
            printFormatted( i + "", COLUMN_WIDTH );
        }
        System.out.println();
    }

    private void printHorizontalRule( int number )
    {
        int leftCornerLength = COLUMN_WIDTH + 2;
        int numbersLength = number * COLUMN_WIDTH;
        for (int i = 0; i < leftCornerLength + numbersLength; i++)
        {
            System.out.print( "-" );
        }
        System.out.println();
    }

    private void printFormatted( String content, int columnWidth )
    {
        System.out.print( leftJustify( content, columnWidth ) );
    }

    private String leftJustify( String value, int width )
    {
        int spacesToAdd = Math.max( width - value.length(), 0 );
        return value + String.format( "%" + spacesToAdd + "s", " " );
    }
}