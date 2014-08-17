import database.Dao;

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
            Dao dao = new Dao();
            dao.printEventSessions( "Event Sessions before generation" );
            dao.createRandomEventSessions(1000 );
            dao.printEventSessions( "Event Sessions after generation" );
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }
}