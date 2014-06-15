public class StringFormatDemo
{
    //First parameter: character to use as spacing, how many spaces, justification
    //Second parameter: string to format

    public static void main( String[] args )
    {
        System.out.println( "[" + String.format( "%4d", 1 ) + "," + String.format( "%06d", 2 ) + "]" );

                            //Right justify                         //Left justify
        System.out.println( "[" + String.format( "%4s", 1 ) + "," + String.format( "%-6s", 2 ) + "]" );
    }
}
