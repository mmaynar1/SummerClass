package reportdetails;

public class EventTypeReportDetail
{
    private final String clubName;
    private final String eventTypeName;
    private final int count;

    public EventTypeReportDetail( String clubName, String eventTypeName, int count )
    {
        this.clubName = clubName;
        this.eventTypeName = eventTypeName;
        this.count = count;
    }

    public String getClubName()
    {
        return clubName;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    public int getCount()
    {
        return count;
    }
}
