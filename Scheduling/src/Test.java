import java.sql.SQLException;

public class Test
{
    public static void main( String[] args )
    {
        try
        {
            new Test().exerciseDatabase();
            Reports reports = new Reports();
            reports.generateEventTypeReport();
            reports.generateEventTypeAndStatusReport();
            reports.generateMemberPendingEventsReport();
            reports.generateStatusCountReport();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void exerciseDatabase() throws SQLException
    {
        DAO dao = new DAO();
        try
        {
            //1
            dao.printEventTypes( "Event Types" );
            System.out.println();

            //4
            String farahId = dao.getMemberId( "Farah" );
            String clubId3001 = dao.getClubId( 3001 );
            System.out.println( "Farah's event count at 3001" );
            System.out.println( dao.getEventsCount( farahId, clubId3001 ) );
            System.out.println();

            //5
            System.out.println( "Farah's pending events before cancels" );
            System.out.println( dao.getPendingEventsCount( farahId ) );
            dao.cancelPendingEvents( farahId );
            System.out.println( "Farah's pending events after cancels" );
            System.out.println( dao.getPendingEventsCount( farahId ) );
            System.out.println();

            //6
            System.out.println( "Employees teaching kick boxing at 3002" );
            System.out.println( dao.getEmployees( "Kick Boxing", 3002 ) );
            System.out.println();

            //7
            dao.addHourToStartTime( "Pilates", 3000 );

            //8
            System.out.println( "Delete Ellen's events" );
            System.out.println( "Count before" );
            System.out.println( dao.getEventsCount( "Ellen" ) );
            dao.deleteEvents( "Ellen" );
            System.out.println( "Count after" );
            System.out.println( dao.getEventsCount( "Ellen" ) );
            System.out.println();

            //9
            System.out.println();
            String jackieMemberId = dao.getMemberId( "Jackie" );
            String clubId3000 = dao.getClubId( 3000 );
            System.out.println( "Events at 3000 before" );
            System.out.println( dao.getEventsCount( jackieMemberId, clubId3000 ) );
            System.out.println( "Events at 3001 before" );
            System.out.println( dao.getEventsCount( jackieMemberId, clubId3001 ) );
            dao.deleteEvents( "Jackie", 3000 );
            System.out.println( "Events at 3000 after delete" );
            System.out.println( dao.getEventsCount( jackieMemberId, clubId3000 ) );
            System.out.println( "Events at 3001 after delete" );
            System.out.println( dao.getEventsCount( jackieMemberId, clubId3001 ) );
            dao.transferEventsAddingAnHour( "Jackie", 3001, 3000 );
            System.out.println( "Events at 3000 after transfer" );
            System.out.println( dao.getEventsCount( jackieMemberId, clubId3000 ) );
            System.out.println( "Events at 3001 after transfer" );
            System.out.println( dao.getEventsCount( jackieMemberId, clubId3001 ) );

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
