import java.io.FileInputStream;
import java.lang.Class;
import java.lang.Exception;
import java.lang.String;
import java.sql.*;
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
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return connection;
    }

    /*private int readInt( String sql, Object... objects ) throws SQLException
    {
        ResultSet resultSet = read( sql, objects );
        int result = 0;
        try
        {
            if ( resultSet.next() )
            {
                result = resultSet.getInt( 1 );
            }
        }
        finally
        {
            if ( resultSet != null )
            {
                resultSet.close();
            }
        }

        return result;
    }


    private ResultSet read( String sql, Object... objects ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            statement = connection.prepareStatement( sql );

            for (int index = 1; index <= objects.length; ++index)
            {
                statement.setString( index, objects[index - 1] + "" );
            }

            resultSet = statement.executeQuery();
        }
        finally
        {
            close( connection, statement, resultSet );
        }

        return resultSet;
    }*/

    private void close( Connection connection, PreparedStatement statement, ResultSet resultSet ) throws SQLException
    {
        if ( resultSet != null )
        {
            resultSet.close();
        }
        safeClose( statement );

        Database.releaseConnection( connection );
    }

    private void safeClose( PreparedStatement statement ) throws SQLException
    {
        if ( statement != null )
        {
            statement.close();
        }
    }
}
