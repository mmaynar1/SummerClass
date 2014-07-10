import reportdetails.EventTypeAndStatusReportDetail;
import reportdetails.EventTypeReportDetail;
import reportdetails.MemberPendingEventsReportDetail;
import reportdetails.StatusCountReportDetail;
import utility.RandomGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO
{
    public void transferEventsAddingAnHour( String memberFirstName, int fromClubNumber, int toClubNumber ) throws SQLException
    {
        String sql = "insert into eventSessions\n" +
                     "select a.* from (select getGuid(), addtime(es_start,'0 1:0:0.0'), m_id, e_id, s_id, et_id, ? from eventSessions es \n" +
                     "where es.m_id = (select m_id from members where m_first_name = ?)\n" +
                     "and es.c_id = (select c_id from clubs where c_number = ?)) as a";

        update( sql, getClubId( toClubNumber ), memberFirstName, fromClubNumber + "" );
    }

    public void deleteEvents( String memberFirstName, int clubNumber ) throws SQLException
    {
        String sql = "delete from\n" +
                     "eventSessions\n" +
                     "where m_id = (select m_id from members where m_first_name = ?)\n" +
                     "and c_id = (select c_id from clubs where c_number = ?)";

        update( sql, memberFirstName, clubNumber + "" );
    }

    public void deleteEvents( String memberFirstName ) throws SQLException
    {
        String sql = "delete from\n" +
                     "eventSessions\n" +
                     "where m_id = (select m_id from members where m_first_name = ?)";

        update( sql, memberFirstName );
    }

    public int getEventsCount( String memberFirstName ) throws SQLException
    {
        String sql = "select count(*) as cnt\n" +
                     "from eventSessions es\n" +
                     "where es.m_id = (select m_id from members where m_first_name = ?)";

        return readInt( sql, memberFirstName );
    }

    public void addHourToStartTime( String eventTypeName, int clubNumber ) throws SQLException
    {
        String sql = "update eventSessions es\n" +
                     "set es.es_start = addtime(es.es_start,'0 1:0:0.0')\n" +
                     "where es.et_id = (select et_id from eventTypes where et_name = ?)\n" +
                     "and es.c_id = (select c_id from clubs where c_number = ?)";

        update( sql, eventTypeName, clubNumber + "" );
    }

    private int update( String sql, String... parameters ) throws SQLException
    {
        Connection connection = Database.getConnection();
        PreparedStatement statement = null;
        int rowsUpdated = 0;
        try
        {
            statement = connection.prepareStatement( sql );
            for (int index = 1; index <= parameters.length; ++index)
            {
                statement.setString( index, parameters[index - 1] );
            }
            rowsUpdated = statement.executeUpdate();
        }
        finally
        {
            safeClose( statement );
            Database.releaseConnection( connection );
        }

        return rowsUpdated;
    }

    private List<String> getStrings( ResultSet resultSet, String columnLabel ) throws SQLException
    {
        List<String> results = new ArrayList<String>();
        while ( resultSet.next() )
        {
            results.add( resultSet.getString( columnLabel ) );
        }

        resultSet.close();

        return results;
    }

    public List<String> getEmployees( String eventTypeName, int clubNumber ) throws SQLException
    {
        String sql = "select distinct concat(e_first_name,\" \",  e_last_name) name from eventSessions es\n" +
                     "join eventTypes et on et.et_id = es.et_id\n" +
                     "join employees e on e.e_id = es.e_id\n" +
                     "join clubs c on c.c_id = es.c_id\n" +
                     "where et_name = ?\n" +
                     "and c_number = ?";

        ResultSet resultSet = read( sql, eventTypeName, clubNumber );
        return getStrings( resultSet, "name" );

    }

    public int getPendingEventsCount( String memberId ) throws SQLException
    {
        String sql = "select count(*) as cnt from eventSessions es\n" +
                     "where es.m_id = ? \n" +
                     "and es.s_id = (select s_id from statuses where s_abc_code = ? )";

        return readInt( sql, memberId, Statuses.pending.getAbcCode() );
    }

    private int readInt( String sql, Object... objects ) throws SQLException
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
            close( resultSet );
        }

        return result;
    }

    private String readString( String sql, Object... objects ) throws SQLException
    {
        ResultSet resultSet = read( sql, objects );
        String result = null;
        try
        {
            if ( resultSet.next() )
            {
                result = resultSet.getString( 1 );
            }
        }
        finally
        {
            close( resultSet );
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
    }

    public void cancelPendingEvents( String memberId ) throws SQLException
    {
        String sql = "update eventSessions es\n" +
                     "set es.s_id = (select s_id from statuses where s_abc_code = ?)\n" +
                     "where es.m_id = ?\n" +
                     "and es.s_id = (select s_id from statuses where s_abc_code = ?)";

        update( sql, Statuses.cancelled.getAbcCode(), memberId, Statuses.pending.getAbcCode() );
    }

    public void createRandomEventSessions() throws SQLException
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

        update( sql );
    }


    public int getEventsCount( String memberId, String clubId ) throws SQLException
    {
        String sql = "select count(*) as cnt from\n" +
                     "eventSessions es \n" +
                     "join members m on m.m_id = es.m_id\n" +
                     "where m.m_id = ?\n" +
                     "and es.c_id = ?";

        return readInt( sql, memberId, clubId );
    }

    public String getClubId( int clubNumber ) throws SQLException
    {
        String sql = "select c_id from clubs where c_number = ?";
        return readString( sql, clubNumber );
    }

    public String getMemberId( String firstName ) throws SQLException
    {
        String sql = "select m_id from members where m_first_name = ?";
        return readString( sql, firstName );
    }


    public void addMembers( List<Name> names ) throws SQLException
    {
        for (Name name : names)
        {
            addMember( name.getFirstName(), name.getLastName() );
        }
    }

    private void addMember( String firstName, String lastName ) throws SQLException
    {
        String sql = "insert into members (m_id, m_first_name, m_last_name) values( ?, ?, ? )";

        String memberId = RandomGenerator.getGuid();
        int rowsUpdated = update( sql, memberId, firstName, lastName );

        if ( rowsUpdated != 1 )
        {
            throw new SQLException( "No rows inserted" );
        }
    }

    public void printMembers( String caption ) throws SQLException
    {
        ResultSet resultSet = null;
        try
        {
            String sql = "select m_id, m_first_name, m_last_name from members";
            resultSet = read( sql );

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
            close( resultSet );
        }
    }

    public void printEventSessions( String caption ) throws SQLException
    {
        ResultSet resultSet = null;
        try
        {
            String sql = "select es_id,es_start,m_id,e_id,s_id,et_id,c_id from eventSessions";
            resultSet = read( sql );

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
            close( resultSet );
        }

    }

    public void printEventTypes( String caption ) throws SQLException
    {
        ResultSet resultSet = null;
        try
        {
            String sql = "select et_id, et_name from eventTypes";
            resultSet = read( sql );

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
            close( resultSet );
        }
    }

    private void printEmployees( String caption ) throws SQLException
    {
        ResultSet resultSet = null;

        try
        {
            String sql = "select e_id, e_first_name, e_last_name from employees";
            resultSet = read( sql );

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
            close( resultSet );
        }
    }

    private void safeClose( PreparedStatement statement ) throws SQLException
    {
        if ( statement != null )
        {
            statement.close();
        }
    }

    public List<EventTypeReportDetail> getEventTypeReportDetails() throws SQLException
    {
        ResultSet resultSet = null;
        List<EventTypeReportDetail> details = new ArrayList<EventTypeReportDetail>();
        try
        {
            String sql = "select c.c_name clubName,\n" +
                         "et.et_name eventType,\n" +
                         "(select count(*)\n" +
                         "from eventSessions sub_es\n" +
                         "where sub_es.c_id = c.c_id\n" +
                         "and sub_es.et_id = et.et_id\n" +
                         ") counts\n" +
                         "from clubs c\n" +
                         "join eventTypes et\n" +
                         "join statuses s\n" +
                         "group by clubName, eventType\n" +
                         "order by clubName, eventType";

            resultSet = read( sql );
            while ( resultSet.next() )
            {
                String clubName = resultSet.getString( "clubName" );
                String eventTypeName = resultSet.getString( "eventType" );
                int count = resultSet.getInt( "counts" );
                details.add( new EventTypeReportDetail( clubName, eventTypeName, count ) );
            }
        }
        finally
        {
            close( resultSet );
        }

        return details;
    }

    private void close( Connection connection, PreparedStatement statement, ResultSet resultSet ) throws SQLException
    {
        close( resultSet );
        safeClose( statement );

        Database.releaseConnection( connection );
    }

    public List<EventTypeAndStatusReportDetail> getEventTypeAndStatusReportDetails() throws SQLException
    {
        ResultSet resultSet = null;
        List<EventTypeAndStatusReportDetail> details = new ArrayList<EventTypeAndStatusReportDetail>();
        try
        {
            String sql = "select c.c_number clubNumber,\n" +
                         "et.et_name eventType,\n" +
                         "s.s_name eventStatus,\n" +
                         "(select count(*)\n" +
                         "from eventSessions sub_es\n" +
                         "where sub_es.c_id = c.c_id\n" +
                         "and sub_es.et_id = et.et_id\n" +
                         "and sub_es.s_id = s.s_id\n" +
                         ") counts\n" +
                         "from clubs c\n" +
                         "join eventTypes et\n" +
                         "join statuses s\n" +
                         "group by clubNumber, eventType, eventStatus";

            resultSet = read( sql );
            while ( resultSet.next() )
            {
                String clubNumber = resultSet.getString( "clubNumber" );
                String eventTypeName = resultSet.getString( "eventType" );
                String statusName = resultSet.getString( "eventStatus" );
                int count = resultSet.getInt( "counts" );
                details.add( new EventTypeAndStatusReportDetail( clubNumber, eventTypeName, statusName, count ) );
            }
        }
        finally
        {
            close( resultSet );
        }

        return details;
    }

    private void close( ResultSet resultSet ) throws SQLException
    {
        if ( resultSet != null )
        {
            resultSet.close();
        }
    }

    public List<MemberPendingEventsReportDetail> getMemberPendingEventsReportDetails() throws SQLException
    {
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

            resultSet = read( sql );
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
           close( resultSet );
        }

        return details;
    }

    public List<StatusCountReportDetail> getStatusCountReportDetails() throws SQLException
    {
        ResultSet resultSet = null;
        List<StatusCountReportDetail> details = new ArrayList<StatusCountReportDetail>();
        try
        {
            String sql = "select s_name,\n" +
                         "(select format(count(*) ,0) \n" +
                         "from eventSessions es\n" +
                         "where  es.s_id = s.s_id) as cnt\n" +
                         "from statuses s \n" +
                         "group by s_name\n" +
                         "union\n" +
                         "select 'All', format(count(*),0) as cnt\n" +
                         "from eventSessions es \n" +
                         "join statuses s on s.s_id = es.s_id";

            resultSet = read( sql );
            while ( resultSet.next() )
            {
                String statusName = resultSet.getString( "s_name" );
                String count = resultSet.getString( "cnt" );
                details.add( new StatusCountReportDetail( statusName, count ) );
            }
        }
        finally
        {
            close( resultSet );
        }

        return details;
    }

    public void addEmployees( List<Name> names ) throws SQLException
    {
        Connection connection = Database.getConnection();


        String sql ="insert into employees (e_id,e_first_name,e_last_name)\n" +
                    "values(getGuid(), ?, ?)";

        PreparedStatement statement = connection.prepareStatement( sql );

        for(Name name : names)
        {
            statement.setString( 1, name.getFirstName() );
            statement.setString( 2, name.getLastName() );
            statement.addBatch();
            statement.clearParameters();
        }

        int[] results = statement.executeBatch();

        for (int i = 0; i < results.length; i++)
        {
            if(results[i]!=1)
            {
                throw new RuntimeException( "Add employee failed" );
            }
        }
    }
}