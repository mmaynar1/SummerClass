package utility;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSupport
{
    public static void main( String[] args )
    {
        String line = "stuff.3::junk.7::more.9";
        String[] words = line.split( "::" );
        for (String word : words)
        {
            String[] subwords = word.split( "\\." );
            System.out.println( subwords[0] + " " + subwords[1] );
        }

        // http://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
        System.out.println( "Working Directory = " + System.getProperty( "user.dir" ) );

        List<String> outputLines = Arrays.asList( "This is the first line.", "Second line.", "Third line here." );
        String fileName = "junk.txt";
        boolean worked = putText( outputLines, fileName, false );
        if ( worked )
        {
            System.out.println( "putText worked" );
        }
        else
        {
            System.out.println( "putText failed" );
        }

        List<String> inputLines = getText( fileName );
        if ( inputLines != null )
        {
            System.out.println( "getText worked" );
        }
        else
        {
            System.out.println( "getText failed" );
        }

        for (int i = 0; i < outputLines.size(); ++i)
        {
            if ( inputLines == null || !outputLines.get( i ).equals( inputLines.get( i ) ) )
            {
                System.out.println( "Input does NOT equal output" );
                break;
            }
        }
    }

    public static List<String> getText( String fileName )
    {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader( new FileReader( fileName ) );
            String line;
            while ( (line = reader.readLine()) != null )
            {
                lines.add( line );
            }
        }
        catch (Exception exception)
        {
            lines = null;
        }
        finally
        {
            if ( reader != null )
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioException)
                {
                    lines = null;
                    ioException.printStackTrace();
                }
            }
        }

        return lines;
    }

    public static boolean putText( List<String> lines, String fileName, boolean appendMode )
    {
        boolean worked = true;

        BufferedWriter writer = null;
        try
        {
            final String NEWLINE = "\n";
            writer = new BufferedWriter( new FileWriter( fileName, appendMode ) );
            for (String line : lines)
            {
                writer.write( line + NEWLINE );
            }
        }
        catch (Exception exception)
        {
            worked = false;
            exception.printStackTrace();
        }
        finally
        {
            if ( writer != null )
            {
                try
                {
                    writer.close();
                }
                catch (IOException ioException)
                {
                    worked = false;
                    ioException.printStackTrace();
                }
            }
        }

        return worked;
    }

    public static String leftJustify( String value, int width )
    {
        return String.format( "%-" + width + "s", value );
    }

    public static boolean putObject( String fileName, Object object )
    {
        boolean worked = true;

        ObjectOutputStream writer = null;
        try
        {
            writer = new ObjectOutputStream( new FileOutputStream( fileName ) );
            writer.writeObject( object );
        }
        catch (Exception exception)
        {
            worked = false;
            exception.printStackTrace();
        }
        finally
        {
            if ( writer != null )
            {
                try
                {
                    writer.close();
                }
                catch (IOException ioException)
                {
                    worked = false;
                    ioException.printStackTrace();
                }
            }
        }

        return worked;
    }

    public static Object getObject( String fileName )
    {
        Object object = null;

        ObjectInputStream reader = null;
        try
        {
            reader = new ObjectInputStream( new FileInputStream( fileName ) );
            object = reader.readObject();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            if ( reader != null )
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioException)
                {
                    object = null;
                    ioException.printStackTrace();
                }
            }
        }

        return object;
    }
}
