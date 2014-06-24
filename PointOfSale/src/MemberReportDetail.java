import java.math.BigDecimal;

public final class MemberReportDetail
{
    private final String memberName;
    private int salesCount;
    private int saleItemCount;
    private BigDecimal total;
    private final String clubId;

    public MemberReportDetail( Sale sale )
    {
        this( sale.getClubId(), sale.getMemberName(), sale.getSaleItemCount(), sale.getTotal() );
    }

    public MemberReportDetail( String clubId, String memberName, int salesCount, int saleItemCount, BigDecimal total )
    {
        this.memberName = memberName;
        this.clubId = clubId;
        setSalesCount( salesCount );
        setSaleItemCount( saleItemCount );
        setTotal( total );
    }

    public MemberReportDetail( String clubId, String memberName, int saleItemCount, BigDecimal total )
    {
        this( clubId, memberName, 1, saleItemCount, total );
    }

    public MemberReportDetail( String clubId, String memberName, int salesCount, int saleItemCount, double total )
    {
        this( clubId, memberName, salesCount, saleItemCount, new BigDecimal( total ) );
    }

    public MemberReportDetail( String clubId, String memberName, int saleItemCount, double total )
    {
        this( clubId, memberName, 1, saleItemCount, new BigDecimal( total ) );
    }

    public void update( Sale sale )
    {
        incrementSalesCount();
        setSaleItemCount( getSaleItemCount() + sale.getSaleItemCount() );
        setTotal( getTotal().add( sale.getTotal() ) );
    }

    private void incrementSalesCount()
    {
        setSalesCount( getSalesCount() + 1 );
    }

    public int getSalesCount()
    {
        return salesCount;
    }

    private void setSalesCount( int salesCount )
    {
        this.salesCount = salesCount;
    }

    public int getSaleItemCount()
    {
        return saleItemCount;
    }

    private void setSaleItemCount( int saleItemCount )
    {
        this.saleItemCount = saleItemCount;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    private void setTotal( BigDecimal total )
    {
        this.total = total;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public String getClubId()
    {
        return clubId;
    }
}