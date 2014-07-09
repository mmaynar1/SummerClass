package reportdetails;

public class StatusCountReportDetail
{
    private final String statusName;
    private final String count;

    public StatusCountReportDetail( String statusName, String count )
    {
        this.statusName = statusName;
        this.count = count;
    }

    public String getStatusName()
    {
        return statusName;
    }

    public String getCount()
    {
        return count;
    }
}
