import java.math.BigDecimal;

public class PaymentMethodReportDetail
{
    private final String paymentMethod;
    private int paymentItemCount;
    private BigDecimal total;
    private final String clubId;

    public PaymentMethodReportDetail( String clubId, String paymentMethod, int paymentItemCount, BigDecimal total )
    {
        this.paymentMethod = paymentMethod;
        setPaymentItemCount( paymentItemCount );
        setTotal( total );
        this.clubId = clubId;
    }

    public PaymentMethodReportDetail(String clubId, String paymentMethod, BigDecimal total)
    {
        this(clubId, paymentMethod, 1, total);
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public int getPaymentItemCount()
    {
        return paymentItemCount;
    }

    private void setPaymentItemCount( int paymentItemCount )
    {
        this.paymentItemCount = paymentItemCount;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    private void setTotal( BigDecimal total )
    {
        this.total = total;
    }

    public void update( PaymentDetail paymentDetail )
    {
        incrementPaymentItemCount();
        setTotal( (getTotal().add( paymentDetail.getCost() )).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP ) );
    }

    private void incrementPaymentItemCount()
    {
        setPaymentItemCount( getPaymentItemCount() + 1 );
    }

    public String getClubId()
    {
        return clubId;
    }
}
