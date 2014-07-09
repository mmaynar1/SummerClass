

import java.sql.*;

public class WriteDemo
{
    public static void main( String[] args )
    {
        WriteDemo demo = new WriteDemo();
        demo.go();
    }

    private void go()
    {
        Connection connection = Database.getConnection();
        try
        {
            exerciseDatabase( connection );
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

    private void exerciseDatabase( Connection connection ) throws SQLException
    {
        deleteEmployees( connection );
        printEmployees( connection, "Before first add" );
        String employeeId = addRandomEmployee( connection );
        printEmployees( connection, "After first add" );
        addRandomEmployee( connection );
        printEmployees( connection, "After second add" );
        updateEmployee( connection, employeeId );
        printEmployees( connection, "After update" );
        deleteEmployee( connection, employeeId );
        printEmployees( connection, "After delete" );
    }

    private void printEmployees( Connection connection, String caption ) throws SQLException
    {
        Statement statement = null;
        ResultSet resultSet = null;

        try
        {
            String query = "select e_id, e_first_name, e_last_name from employees";
            statement = connection.createStatement();
            resultSet = statement.executeQuery( query );

            System.out.println( "\n" + caption );
            int index = 1;
            while ( resultSet.next() )
            {
                String id = resultSet.getString( "e_id" );
                String firstName = resultSet.getString( "e_first_name" );
                String lastName = resultSet.getString( "e_last_name" );

                System.out.format( "%d %s, %s\n", index, id, firstName + " " + lastName );
                ++index;
            }
        }
        finally
        {
            if ( resultSet != null )
            {
                resultSet.close();
            }

            if ( statement != null )
            {
                statement.close();
            }
        }
    }

    private void deleteEmployees( Connection connection ) throws SQLException
    {
        PreparedStatement statement = null;

        try
        {
            String sql = "delete from employees where e_first_name in ('John', 'Robert')";

            statement = connection.prepareStatement( sql );

            statement.executeUpdate();
        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }
        }
    }

    private void deleteEmployee( Connection connection, String employeeId ) throws SQLException
    {
        PreparedStatement statement = null;

        try
        {
            String sql = "delete from employees where e_id = ?";

            statement = connection.prepareStatement( sql );
            statement.setString( 1, employeeId );

            int rowsUpdated = statement.executeUpdate();
            if ( rowsUpdated != 1 )
            {
                throw new SQLException( "No rows deleted" );
            }
        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }
        }
    }

    private void updateEmployee( Connection connection, String employeeId ) throws SQLException
    {
        PreparedStatement statement = null;

        try
        {
            String firstName = "NowItsBobby";

            String sql = "update employees set e_first_name = ?  where e_id = ?";

            statement = connection.prepareStatement( sql );
            statement.setString( 1, firstName );
            statement.setString( 2, employeeId );

            int rowsUpdated = statement.executeUpdate();
            if ( rowsUpdated != 1 )
            {
                throw new SQLException( "No rows updated" );
            }
        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }
        }
    }

    private String addRandomEmployee( Connection connection ) throws SQLException
    {
        PreparedStatement statement = null;
        String result = null;

        try
        {
            String id = RandomGenerator.getGuid();
            String firstName = RandomGenerator.getName();
            String lastName = RandomGenerator.getName();

            String sql = "insert into employees (e_id, e_first_name, e_last_name) values( ?, ?, ? )";

            statement = connection.prepareStatement( sql );
            statement.setString( 1, id );
            statement.setString( 2, firstName );
            statement.setString( 3, lastName );

            int rowsUpdated = statement.executeUpdate();
            if ( rowsUpdated != 1 )
            {
                throw new SQLException( "No rows inserted" );
            }

            result = id;
        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }
        }

        return result;
    }
}
