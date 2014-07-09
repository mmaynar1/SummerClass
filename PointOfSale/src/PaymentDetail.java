import java.math.BigDecimal;

public final class PaymentDetail
{
    public static final String DELIMITER = "#";

    private final String paymentMethodAbcCode;
    private final BigDecimal cost;
    private final BigDecimal payment;
    private final BigDecimal change;

    public PaymentDetail( String paymentMethodAbcCode, BigDecimal cost, BigDecimal payment )
    {
        this.paymentMethodAbcCode = paymentMethodAbcCode;
        this.cost = cost;
        this.payment = payment;
        this.change = setChange();
    }

    public String getTextRepresentation()
    {
        return DELIMITER + getPaymentMethodAbcCode() + DELIMITER +
               getCost() + DELIMITER +
               getPayment() ;
    }


    private BigDecimal setChange()
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

        return change;
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
        return getPaymentMethodAbcCode();
    }

    public String getName()
    {
        return getPaymentMethod().getName();
    }

    public PaymentMethod getPaymentMethod()
    {
        return PaymentMethod.getPaymentMethod( getAbcCode() );
    }

    private String getPaymentMethodAbcCode()
    {
        return paymentMethodAbcCode;
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