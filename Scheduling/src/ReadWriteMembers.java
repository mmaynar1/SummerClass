import utility.FileSupport;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ReadWriteMembers
{
    public static void main( String[] args )
    {
       //2
       new ReadWriteMembers().go();
    }

    private void go()
    {
        Connection connection = Database.getConnection();
        try
        {
            add( connection );
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            Database.releaseConnection( connection );
        }

    }

    private void add( Connection connection )
    {
        try
        {
            Properties properties = new Properties();
            properties.load( new FileInputStream( "input.properties" ) );
            List<Name> names = getNames( FileSupport.getText( properties.getProperty( "memberNames" ) ) );
            DAO dao = new DAO();
            dao.printMembers( connection, "Members before add" );
            dao.addMembers( connection, names );
            dao.printMembers( connection, "Members after add" );
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private List<Name> getNames( List<String> memberNamesFile )
    {
        List<Name> names = new ArrayList<Name>();
        for (String line : memberNamesFile)
        {
            List<String> memberName = Arrays.asList( line.split( ":" ) );
            Name name = new Name( memberName.get( 0 ), memberName.get( 1 ) );
            names.add( name );
        }
        return names;
    }


}
