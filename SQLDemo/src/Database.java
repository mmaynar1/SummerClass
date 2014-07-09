import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

final public class Database
{
    public static void releaseConnection( Connection connection )
    {
        try
        {
            connection.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        Connection connection = null;

        try
        {
            Properties properties = new Properties();
            properties.load( new FileInputStream( "database.properties" ) );

            final String jdbcDriver = properties.getProperty( "driver" );
            final String databaseUrl = properties.getProperty( "database" );
            final String databaseId = properties.getProperty( "id" );
            final String databasePassword = properties.getProperty( "password" );

            Class.forName( jdbcDriver );

            connection = DriverManager.getConnection( databaseUrl, databaseId, databasePassword );
        }
        catch ( Exception exception )
        {
            exception.printStackTrace();
        }

        return connection;
    }
}
