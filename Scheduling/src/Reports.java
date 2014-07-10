import reportdetails.EventTypeAndStatusReportDetail;
import reportdetails.EventTypeReportDetail;
import reportdetails.MemberPendingEventsReportDetail;
import reportdetails.StatusCountReportDetail;
import utility.FileSupport;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Reports
{
    public static final int WIDTH = 22;
    public static final boolean APPEND_MODE = true;
    public static final boolean REPLACE_MODE = false;


    private void generateReportHeader( String title, List<String> headers, String fileName )
    {

        FileSupport.putText( Arrays.asList( title ), fileName, REPLACE_MODE );

        String line = "";
        for (String header : headers)
        {
            line += FileSupport.leftJustify( header, WIDTH );
        }

        FileSupport.putText( Arrays.asList( line ), fileName, APPEND_MODE );
    }

    public void generateStatusCountReport()
    {
        try
        {
            Dao dao = new Dao();
            List<StatusCountReportDetail> details = dao.getStatusCountReportDetails();
            List<String> line = new ArrayList<String>();
            for (StatusCountReportDetail detail : details)
            {
                line.add( FileSupport.leftJustify( detail.getStatusName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getCount()+"", WIDTH ));
            }

            Properties properties = new Properties();
            properties.load( new FileInputStream( "output.properties" ) );
            String fileName = properties.getProperty( "statusCountReport" );
            generateReportHeader( "Status Count Report",
                                  Arrays.asList( "Status Name", "Count" ),
                                  fileName );
            FileSupport.putText( line, fileName, APPEND_MODE );
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void generateMemberPendingEventsReport()
    {
        try
        {
            Dao dao = new Dao();
            List<MemberPendingEventsReportDetail> details = dao.getMemberPendingEventsReportDetails();
            List<String> line = new ArrayList<String>();
            for (MemberPendingEventsReportDetail detail : details)
            {
                line.add( FileSupport.leftJustify( detail.getMemberName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getClubNumber() + "", WIDTH ) +
                          FileSupport.leftJustify( detail.getEventTypeName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getEmployeeName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getStartDateTime(), WIDTH ) +
                          FileSupport.leftJustify( detail.getStatusName(), WIDTH ) );


            }

            Properties properties = new Properties();
            properties.load( new FileInputStream( "output.properties" ) );
            String fileName = properties.getProperty( "memberPendingEventsReport" );
            generateReportHeader( "Member Pending Events Report",
                                  Arrays.asList( "Member", "Club Number", "Event Type Name", "Employee Name", "Start Date Time", "Status Name" ),
                                  fileName );
            FileSupport.putText( line, fileName, APPEND_MODE );
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void generateEventTypeAndStatusReport()
    {
        try
        {
            Dao dao = new Dao();
            List<EventTypeAndStatusReportDetail> details = dao.getEventTypeAndStatusReportDetails();
            List<String> line = new ArrayList<String>();
            for (EventTypeAndStatusReportDetail detail : details)
            {
                line.add( FileSupport.leftJustify( detail.getClubName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getEventTypeName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getStatusName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getCount() + "", WIDTH ) );
            }

            Properties properties = new Properties();
            properties.load( new FileInputStream( "output.properties" ) );
            String fileName = properties.getProperty( "eventTypeAndStatusReport" );
            generateReportHeader( "Event Type and Status Report", Arrays.asList( "Club", "Event Type", "Status", "Count" ), fileName );
            FileSupport.putText( line, fileName, APPEND_MODE );
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void generateEventTypeReport()
    {
        try
        {
            Dao dao = new Dao();
            List<EventTypeReportDetail> details = dao.getEventTypeReportDetails();
            List<String> line = new ArrayList<String>();
            for (EventTypeReportDetail detail : details)
            {
                line.add( FileSupport.leftJustify( detail.getClubName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getEventTypeName(), WIDTH ) +
                          FileSupport.leftJustify( detail.getCount() + "", WIDTH ) );


            }

            Properties properties = new Properties();
            properties.load( new FileInputStream( "output.properties" ) );
            String fileName = properties.getProperty( "eventTypeReport" );
            generateReportHeader( "Event Type Report", Arrays.asList( "Club", "Event Type", "Count" ), fileName );
            FileSupport.putText( line, fileName, APPEND_MODE );
        }
        catch (Exception exception)
        {
            throw new RuntimeException( "Unable to generate event type report" );
        }
    }
}
