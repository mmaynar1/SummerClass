import java.sql.Connection;

public class GenerateEventSessions
{
    public static void main( String[] args )
    {
        //3
        new GenerateEventSessions().go();
    }

    private void go()
    {
        Connection connection = Database.getConnection();
        try
        {
            DAO dao = new DAO();
            dao.printEventSessions( connection, "Event Sessions before generation" );
            dao.createRandomEventSessions( connection );
            dao.printEventSessions( connection, "Event Sessions after generation" );
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
}