import java.sql.*;

public class ConnectDemo
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException
    {
        final String myDriver = "org.mariadb.jdbc.Driver";
        final String myUrl = "jdbc:mariadb://localhost/abcdb";
        Class.forName( myDriver );

        final String databaseId = "root";
        final String databasePassword = "sql";
        Connection connection = DriverManager.getConnection( myUrl, databaseId, databasePassword );

        String query = "SELECT e_id id, concat_ws( ' ', e_first_name, e_last_name ) name FROM employees";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery( query );
        while ( resultSet.next() )
        {
            String employeeId = resultSet.getString( "id" );
            String employeeName = resultSet.getString( "name" );
            System.out.format( "%s, %s\n", employeeId, employeeName );
        }

        // todo move this to a finally block
        resultSet.close();
        statement.close();
        connection.close();
    }
}