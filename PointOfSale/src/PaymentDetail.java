import java.math.BigDecimal;

public final class PaymentDetail
{
    private final PaymentMethod paymentMethod;
    private final BigDecimal amount;

    public PaymentDetail( PaymentMethod paymentMethod, BigDecimal amount )
    {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public PaymentDetail( PaymentMethod paymentMethod, double amount)
    {
        this(paymentMethod, new BigDecimal( amount ));
    }

    public String getAbcCode()
    {
        return getPaymentMethod().getAbcCode();
    }

    public String getName()
    {
        return getPaymentMethod().getName();
    }

    public PaymentMethod getPaymentMethod()
    {
        return paymentMethod;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
}