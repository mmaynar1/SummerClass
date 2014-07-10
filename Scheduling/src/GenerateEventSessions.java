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
        try
        {
            DAO dao = new DAO();
            dao.printEventSessions( "Event Sessions before generation" );
            dao.createRandomEventSessions( );
            dao.printEventSessions( "Event Sessions after generation" );
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}