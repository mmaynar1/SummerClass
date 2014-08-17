package lab3;

import database.Dao;

import java.sql.SQLException;

public class CreateEventsThread implements Runnable
{
    public static final int NUMBER_OF_EVENTS = 10;
    private static int COUNT;

    public CreateEventsThread()
    {
        new Thread( this ).start();

    }

    public boolean isDone()
    {
        return COUNT == NUMBER_OF_EVENTS;
    }

    @Override
    public void run()
    {
        while ( COUNT < NUMBER_OF_EVENTS )
        {
            synchronized (CreateEventsThread.class)
            {
                if ( COUNT < NUMBER_OF_EVENTS )
                {
                    System.out.println( "Event created" + " " + Thread.currentThread().getId() );
                    Dao dao = new Dao();
                    try
                    {
                        dao.createRandomEventSessions( 1 );
                        System.out.println("Create: " + dao.getPendingEventsCount());
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                    ++COUNT;
                }
            }
            try
            {
                Thread.sleep( 100 );
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println( "Create thread " + Thread.currentThread().getId() + " done" );
    }
}
