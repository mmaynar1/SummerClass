package ReportDetails;

public class EventTypeAndStatusReportDetail
{
    private final String clubName;
    private final String eventTypeName;
    private final String statusName;
    private final int count;

    public EventTypeAndStatusReportDetail( String clubName, String eventTypeName,String statusName, int count )
    {
        this.clubName = clubName;
        this.eventTypeName = eventTypeName;
        this.count = count;
        this.statusName = statusName;
    }

    public String getClubName()
    {
        return clubName;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    public String getStatusName()
    {
        return statusName;
    }

    public int getCount()
    {
        return count;
    }
}
