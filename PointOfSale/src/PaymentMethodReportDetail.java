import java.math.BigDecimal;

public class PaymentMethodReportDetail
{
    private final String paymentMethod;
    private int paymentItemCount;
    private BigDecimal total;

    public PaymentMethodReportDetail( String paymentMethod, int paymentItemCount, BigDecimal total )
    {
        this.paymentMethod = paymentMethod;
        setPaymentItemCount( paymentItemCount );
        setTotal( total );
    }

    public PaymentMethodReportDetail(String paymentMethod, BigDecimal total)
    {
        this(paymentMethod, 1, total);
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
        setTotal( getTotal().add( paymentDetail.getAmount() ) );
    }

    private void incrementPaymentItemCount()
    {
        setPaymentItemCount( getPaymentItemCount() + 1 );
    }
}
