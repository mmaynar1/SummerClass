import java.math.BigDecimal;

public final class PaymentDetail
{
    private final String paymentMethodId;
    private final BigDecimal cost;
    private final BigDecimal payment;
    private BigDecimal change;

    public PaymentDetail( String paymentMethodId, BigDecimal cost, BigDecimal payment )
    {
        this.paymentMethodId = paymentMethodId;
        this.cost = cost;
        this.payment = payment;
        setChange();
    }

    private void setChange()
    {
        BigDecimal change;
        if ( hasPaymentRestrictions() && needsChange() )
        {
            change = getPayment().subtract( getCost() );
        }
        else
        {
            change = BigDecimal.ZERO;
        }

        this.change = change;
    }

    private boolean needsChange()
    {
        return getPayment().compareTo( getCost() ) > 0;
    }

    private boolean hasPaymentRestrictions()
    {
        return (getPaymentMethod().getRoundUpValue().compareTo( BigDecimal.ZERO ) > 0);
    }

    public String getAbcCode()
    {
        return Database.getPaymentMethodAbcCode( getPaymentMethodId() );
    }

    public String getName()
    {
        return Database.getPaymentMethodName( getPaymentMethodId() );
    }

    public PaymentMethod getPaymentMethod()
    {
        return Database.getPaymentMethod( getPaymentMethodId() );
    }

    public String getPaymentMethodId()
    {
        return paymentMethodId;
    }

    public BigDecimal getCost()
    {
        return cost;
    }

    public BigDecimal getPayment()
    {
        return payment;
    }

    public BigDecimal getChange()
    {
        return change;
    }
}