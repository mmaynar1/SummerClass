import ReportDetails.EventTypeAndStatusReportDetail;
import ReportDetails.EventTypeReportDetail;
import ReportDetails.MemberPendingEventsReportDetail;
import ReportDetails.StatusCountReportDetail;
import utility.RandomGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO
{
    public static void main( String[] args )
    {
        DAO demo = new DAO();
        demo.go();
        Reports reports = new Reports();
        reports.generateEventTypeReport();
        reports.generateEventTypeAndStatusReport();
        reports.generateMemberPendingEventsReport();
        reports.generateStatusCountReport();
    }

    private void go()
    {
        try
        {
            exerciseDatabase();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void exerciseDatabase() throws SQLException
    {
        try
        {
            //1
            printEventTypes( "Event Types" );
            System.out.println();

            //4
            String farahId = getMemberId( "Farah" );
            String clubId3001 = getClubId( 3001 );
            System.out.println( "Farah's event count at 3001" );
            System.out.println( getEventsCount( farahId, clubId3001 ) );
            System.out.println();

            //5
            System.out.println( "Farah's pending events before cancels" );
            System.out.println( getPendingEventsCount( farahId ) );
            cancelPendingEvents( farahId );
            System.out.println( "Farah's pending events after cancels" );
            System.out.println( getPendingEventsCount( farahId ) );
            System.out.println();

            //6
            System.out.println( "Employees teaching kick boxing at 3002" );
            System.out.println( getEmployees( "Kick Boxing", 3002 ) );
            System.out.println();

            //7
            addHourToStartTime( "Pilates", 3000 );

            //8
            System.out.println( "Delete Ellen's events" );
            System.out.println( "Count before" );
            System.out.println( getEventsCount( "Ellen" ) );
            deleteEvents( "Ellen" );
            System.out.println( "Count after" );
            System.out.println( getEventsCount( "Ellen" ) );
            System.out.println();

            //9
            System.out.println();
            String jackieMemberId = getMemberId( "Jackie" );
            String clubId3000 = getClubId( 3000 );
            System.out.println( "Events at 3000 before" );
            System.out.println( getEventsCount( jackieMemberId, clubId3000 ) );
            System.out.println( "Events at 3001 before" );
            System.out.println( getEventsCount( jackieMemberId, clubId3001 ) );
            deleteEvents( "Jackie", 3000 );
            System.out.println( "Events at 3000 after delete" );
            System.out.println( getEventsCount( jackieMemberId, clubId3000 ) );
            System.out.println( "Events at 3001 after delete" );
            System.out.println( getEventsCount( jackieMemberId, clubId3001 ) );
            transferEventsAddingAnHour( "Jackie", 3001, 3000 );
            System.out.println( "Events at 3000 after transfer" );
            System.out.println( getEventsCount( jackieMemberId, clubId3000 ) );
            System.out.println( "Events at 3001 after transfer" );
            System.out.println( getEventsCount( jackieMemberId, clubId3001 ) );

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    private void transferEventsAddingAnHour( String memberFirstName, int fromClubNumber, int toClubNumber ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        try
        {

            String sql = "insert into eventSessions\n" +
                         "select a.* from (select getGuid(), addtime(es_start,'0 1:0:0.0'), m_id, e_id, s_id, et_id, ? from eventSessions es \n" +
                         "where es.m_id = (select m_id from members where m_first_name = ?)\n" +
                         "and es.c_id = (select c_id from clubs where c_number = ?)) as a";

            statement = connection.prepareStatement( sql );
            statement.setString( 1, getClubId( toClubNumber ) );
            statement.setString( 2, memberFirstName );
            statement.setString( 3, fromClubNumber + "" );
            statement.executeUpdate();
        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }

            Database.releaseConnection( connection );
        }
    }

    private void deleteEvents( String memberFirstName, int clubNumber ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;

        try
        {
            String sql = "delete from\n" +
                         "eventSessions\n" +
                         "where m_id = (select m_id from members where m_first_name = ?)\n" +
                         "and c_id = (select c_id from clubs where c_number = ?)";

            statement = connection.prepareStatement( sql );
            statement.setString( 1, memberFirstName );
            statement.setString( 2, clubNumber + "" );

            statement.executeUpdate();
        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }

            Database.releaseConnection( connection );
        }
    }

    private void deleteEvents( String memberFirstName ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;

        try
        {
            String sql = "delete from\n" +
                         "eventSessions\n" +
                         "where m_id = (select m_id from members where m_first_name = ?)";

            statement = connection.prepareStatement( sql );
            statement.setString( 1, memberFirstName );

            statement.executeUpdate();

        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }

            Database.releaseConnection( connection );
        }
    }

    private int getEventsCount( String memberFirstName ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try
        {

            String sql = "select count(*) as cnt\n" +
                         "from eventSessions es\n" +
                         "where es.m_id = (select m_id from members where m_first_name = ?)";

            statement = connection.prepareStatement( sql );
            statement.setString( 1, memberFirstName );
            resultSet = statement.executeQuery();
            if ( resultSet.next() )
            {
                count = resultSet.getInt( "cnt" );
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

            Database.releaseConnection( connection );
        }

        return count;
    }

    private void addHourToStartTime( String eventTypeName, int clubNumber ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        try
        {
            String sql = "update eventSessions es\n" +
                         "set es.es_start = addtime(es.es_start,'0 1:0:0.0')\n" +
                         "where es.et_id = (select et_id from eventTypes where et_name = ?)\n" +
                         "and es.c_id = (select c_id from clubs where c_number = ?);";
            statement = connection.prepareStatement( sql );
            statement.setString( 1, eventTypeName );
            statement.setString( 2, clubNumber + "" );
            statement.executeUpdate();

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }

            Database.releaseConnection( connection );
        }
    }

    private List<String> getEmployees( String eventTypeName, int clubNumber ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> names = new ArrayList<String>();
        try
        {
            String sql = "select distinct concat(e_first_name,\" \",  e_last_name) name from eventSessions es\n" +
                         "join eventTypes et on et.et_id = es.et_id\n" +
                         "join employees e on e.e_id = es.e_id\n" +
                         "join clubs c on c.c_id = es.c_id\n" +
                         "where et_name = ?\n" +
                         "and c_number = ?;";
            statement = connection.prepareStatement( sql );
            statement.setString( 1, eventTypeName );
            statement.setString( 2, clubNumber + "" );
            resultSet = statement.executeQuery();

            while ( resultSet.next() )
            {
                names.add( resultSet.getString( "name" ) );
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

            Database.releaseConnection( connection );
        }
        return names;
    }

    private int getPendingEventsCount( String memberId ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try
        {
            String sql = "select count(*) as cnt from eventSessions es\n" +
                         "where es.m_id = ? \n" +
                         "and es.s_id = (select s_id from statuses where s_abc_code = 'PEN' );";
            statement = connection.prepareStatement( sql );
            statement.setString( 1, memberId );
            resultSet = statement.executeQuery();
            if ( resultSet.next() )
            {
                count = resultSet.getInt( "cnt" );
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

            Database.releaseConnection( connection );
        }

        return count;
    }


    private void cancelPendingEvents( String memberId ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            String sql = "update eventSessions es\n" +
                         "set es.s_id = (select s_id from statuses where s_abc_code = 'CAN')\n" +
                         "where es.m_id = ?\n" +
                         "and es.s_id = (select s_id from statuses where s_abc_code = 'PEN')";
            statement = connection.prepareStatement( sql );
            statement.setString( 1, memberId );
            resultSet = statement.executeQuery();
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

            Database.releaseConnection( connection );
        }
    }

    public void createRandomEventSessions( Connection connection ) throws SQLException
    {
        PreparedStatement statement = null;
        try
        {

            String sql = "insert into eventSessions (es_id, es_start, m_id, e_id, s_id, et_id, c_id)\n" +
                         "select a.* from(select getGuid(), now(), m_id, e_id, s_id, et_id, c_id\n" +
                         "from\n" +
                         "members join\n" +
                         "employees join\n" +
                         "statuses join\n" +
                         "eventTypes join\n" +
                         "clubs\n" +
                         "order by rand()) a\n" +
                         "limit 1000";

            statement = connection.prepareStatement( sql );

            statement.executeUpdate();

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            if ( statement != null )
            {
                statement.close();
            }
        }
    }


    private String getEventsCount( String memberId, String clubId ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String count = null;
        try
        {
            String query = "select count(*) as cnt from\n" +
                           "eventSessions es \n" +
                           "join members m on m.m_id = es.m_id\n" +
                           "where m.m_id = ?\n" +
                           "and es.c_id = ?";
            statement = connection.prepareStatement( query );
            statement.setString( 1, memberId );
            statement.setString( 2, clubId );
            resultSet = statement.executeQuery();

            if ( resultSet.next() )
            {
                count = resultSet.getString( "cnt" );
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

            Database.releaseConnection( connection );
        }
        return count;
    }

    private String getClubId( int clubNumber ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String id = null;
        try
        {
            String sql = "select c_id from clubs where c_number = ?";
            statement = connection.prepareStatement( sql );
            statement.setString( 1, (clubNumber + "") );
            resultSet = statement.executeQuery();

            if ( resultSet.next() )
            {
                id = resultSet.getString( "c_id" );
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
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

            Database.releaseConnection( connection );

        }
        return id;
    }


    private String getMemberId( String firstName ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String id = null;
        try
        {
            String sql = "select m_id from members where m_first_name = ?";
            statement = connection.prepareStatement( sql );
            statement.setString( 1, firstName );
            resultSet = statement.executeQuery();

            if ( resultSet.next() )
            {
                id = resultSet.getString( "m_id" );
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
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

            Database.releaseConnection( connection );
        }
        return id;
    }


    public void addMembers( Connection connection, List<Name> names ) throws SQLException
    {
        for (Name name : names)
        {
            addMember( connection, name.getFirstName(), name.getLastName() );
        }
    }

    private void addMember( Connection connection, String firstName, String lastName ) throws SQLException
    {
        PreparedStatement statement = null;

        try
        {
            String m_id = RandomGenerator.getGuid();

            String sql = "insert into members (m_id, m_first_name, m_last_name) values( ?, ?, ? )";

            statement = connection.prepareStatement( sql );
            statement.setString( 1, m_id );
            statement.setString( 2, firstName );
            statement.setString( 3, lastName );

            int rowsUpdated = statement.executeUpdate();
            if ( rowsUpdated != 1 )
            {
                throw new SQLException( "No rows inserted" );
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


    public void printMembers( Connection connection, String caption ) throws SQLException
    {
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            String query = "select m_id, m_first_name, m_last_name from members";
            statement = connection.createStatement();
            resultSet = statement.executeQuery( query );

            System.out.println( "\n" + caption );
            int index = 1;
            while ( resultSet.next() )
            {
                String id = resultSet.getString( "m_id" );
                String firstName = resultSet.getString( "m_first_name" );
                String lastName = resultSet.getString( "m_last_name" );

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

    public void printEventSessions( Connection connection, String caption ) throws SQLException
    {
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            String query = "select es_id,es_start,m_id,e_id,s_id,et_id,c_id from eventSessions";
            statement = connection.createStatement();
            resultSet = statement.executeQuery( query );

            System.out.println( "\n" + caption );
            int index = 1;
            while ( resultSet.next() )
            {
                String es_id = resultSet.getString( "es_id" );
                String es_start = resultSet.getString( "es_start" );
                String m_id = resultSet.getString( "m_id" );
                String e_id = resultSet.getString( "e_id" );
                String s_id = resultSet.getString( "s_id" );
                String et_id = resultSet.getString( "et_id" );
                String c_id = resultSet.getString( "c_id" );

                System.out.format( "%d %s,%s,%s,%s,%s,%s, %s\n", index, es_id, es_start, m_id, e_id, s_id, et_id, c_id );
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


    private void printEventTypes( String caption ) throws SQLException
    {
        Connection connection = Database.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try
        {
            String query = "select et_id, et_name from eventTypes";
            statement = connection.createStatement();
            resultSet = statement.executeQuery( query );

            System.out.println( "\n" + caption );
            int index = 1;
            while ( resultSet.next() )
            {
                String id = resultSet.getString( "et_id" );
                String name = resultSet.getString( "et_name" );

                System.out.format( "%d %s, %s\n", index, id, name );
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

            Database.releaseConnection( connection );
        }
    }

    private void printEmployees( String caption ) throws SQLException
    {
        Connection connection = Database.getConnection();
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

            Database.releaseConnection( connection );
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

    public List<EventTypeReportDetail> getEventTypeReportDetails() throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<EventTypeReportDetail> details = new ArrayList<EventTypeReportDetail>();
        try
        {
            String sql = "select c_name, et_name, count(*) as cnt  from eventSessions es\n" +
                         "join eventTypes et on et.et_id = es.et_id\n" +
                         "join clubs c on c.c_id = es.c_id\n" +
                         "group by c_name, et_name\n" +
                         "order by c_name, et_name";

            statement = connection.prepareStatement( sql );
            resultSet = statement.executeQuery();
            while ( resultSet.next() )
            {
                String clubName = resultSet.getString( "c_name" );
                String eventTypeName = resultSet.getString( "et_name" );
                int count = resultSet.getInt( "cnt" );
                details.add( new EventTypeReportDetail( clubName, eventTypeName, count ) );
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

            Database.releaseConnection( connection );
        }

        return details;
    }

    public List<EventTypeAndStatusReportDetail> getEventTypeAndStatusReportDetails() throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<EventTypeAndStatusReportDetail> details = new ArrayList<EventTypeAndStatusReportDetail>();
        try
        {
            String sql = "select c_name, et_name, s_name, count(*) as cnt  from eventSessions es\n" +
                         "join eventTypes et on et.et_id = es.et_id\n" +
                         "join clubs c on c.c_id = es.c_id\n" +
                         "join statuses s on s.s_id = es.s_id\n" +
                         "group by c_name, et_name, s_name\n" +
                         "order by c_name, et_name, s_name";

            statement = connection.prepareStatement( sql );
            resultSet = statement.executeQuery();
            while ( resultSet.next() )
            {
                String clubName = resultSet.getString( "c_name" );
                String eventTypeName = resultSet.getString( "et_name" );
                String statusName = resultSet.getString( "s_name" );
                int count = resultSet.getInt( "cnt" );
                details.add( new EventTypeAndStatusReportDetail( clubName, eventTypeName, statusName, count ) );
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

            Database.releaseConnection( connection );
        }

        return details;
    }

    public List<MemberPendingEventsReportDetail> getMemberPendingEventsReportDetails() throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<MemberPendingEventsReportDetail> details = new ArrayList<MemberPendingEventsReportDetail>();
        try
        {
            String sql = "select concat(m_first_name, \" \", m_last_name) member_name,\n" +
                         "c_number,\n" +
                         "et_name,\n" +
                         "concat(e_first_name, \" \", e_last_name) employee_name,\n" +
                         "date_format(es_start, '%m/%d/%Y %h:%i %p') start_time,\n" +
                         "s_name\n" +
                         "from eventSessions es\n" +
                         "join members m on m.m_id = es.m_id\n" +
                         "join employees e on e.e_id = es.e_id\n" +
                         "join clubs c on c.c_id = es.c_id\n" +
                         "join eventTypes et on et.et_id = es.et_id\n" +
                         "join statuses s on s.s_id = es.s_id\n" +
                         "order by member_name, c_number, es_start";

            statement = connection.prepareStatement( sql );
            resultSet = statement.executeQuery();
            while ( resultSet.next() )
            {
                String memberName = resultSet.getString( "member_name" );
                int clubNumber = resultSet.getInt( "c_number" );
                String eventTypeName = resultSet.getString( "et_name" );
                String employeeName = resultSet.getString( "employee_name" );
                String startTime = resultSet.getString( "start_time" );
                String statusName = resultSet.getString( "s_name" );
                details.add( new MemberPendingEventsReportDetail( memberName, clubNumber, eventTypeName, employeeName, startTime, statusName ) );
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

            Database.releaseConnection( connection );
        }

        return details;
    }

    public List<StatusCountReportDetail> getStatusCountReportDetails() throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<StatusCountReportDetail> details = new ArrayList<StatusCountReportDetail>();
        try
        {
            String sql = "select s_name, format(count(*) ,0) as count\n" +
                         "from eventSessions es\n" +
                         "join statuses s on s.s_id = es.s_id\n" +
                         "group by s_name\n" +
                         "order by s_name";

            statement = connection.prepareStatement( sql );
            resultSet = statement.executeQuery();
            while ( resultSet.next() )
            {
                String statusName = resultSet.getString( "s_name" );
                String count = resultSet.getString( "count" );
                details.add( new StatusCountReportDetail( statusName, count ) );
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

            Database.releaseConnection( connection );
        }

        return details;
    }
}