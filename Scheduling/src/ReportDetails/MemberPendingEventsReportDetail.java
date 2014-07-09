package reportdetails;

public class MemberPendingEventsReportDetail
{
    private final String memberName;
    private final int clubNumber;
    private final String eventTypeName;
    private final String employeeName;
    private final String startDateTime;
    private final String statusName;

    public MemberPendingEventsReportDetail( String memberName, int clubNumber, String eventTypeName, String employeeName, String startDateTime, String statusName )
    {
        this.memberName = memberName;
        this.clubNumber = clubNumber;
        this.eventTypeName = eventTypeName;
        this.employeeName = employeeName;
        this.startDateTime = startDateTime;
        this.statusName = statusName;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public int getClubNumber()
    {
        return clubNumber;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public String getStartDateTime()
    {
        return startDateTime;
    }

    public String getStatusName()
    {
        return statusName;
    }
}