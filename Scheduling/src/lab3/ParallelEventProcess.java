package lab3;

import java.util.ArrayList;
import java.util.List;

public class ParallelEventProcess
{
    public static void main( String[] args )
    {
        System.out.println("ParallelEventProcess started");
        List<CreateEventsThread> createThreads = new ArrayList<CreateEventsThread>();
        List<UpdateEventsThread> updateThreads = new ArrayList<UpdateEventsThread>();
        for (int i = 0; i < 5; i++)
        {
            CreateEventsThread createThread = new CreateEventsThread();
            createThreads.add( createThread );

            UpdateEventsThread updateThread = new UpdateEventsThread();
            updateThreads.add( updateThread );
        }


        boolean createThreadsDone = new ParallelEventProcess().isCreateThreadsDone( createThreads );
        boolean updateThreadsDone = new ParallelEventProcess().isUpdateEventsDone( updateThreads );
        boolean done  = createThreadsDone && updateThreadsDone;

        while(!done)
        {
            createThreadsDone = new ParallelEventProcess().isCreateThreadsDone( createThreads );
            updateThreadsDone = new ParallelEventProcess().isUpdateEventsDone( updateThreads );
            done = createThreadsDone && updateThreadsDone;
            try
            {
                System.out.println("ParallelEventProcess sleeping");
                Thread.sleep( 100 );

            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("ParallelEventProcess done");

    }

    private boolean isCreateThreadsDone( List<CreateEventsThread> createThreads )
    {
        boolean done = false;
        for (CreateEventsThread thread : createThreads)
        {
            if ( thread.isDone() )
            {
                done = true;
            }
        }
        return done;
    }

    private boolean isUpdateEventsDone( List<UpdateEventsThread> updateThreads )
    {
        boolean done = false;
        for (UpdateEventsThread thread : updateThreads)
        {
            if ( thread.isDone() )
            {
                done = true;
            }
        }
        return done;
    }
}
