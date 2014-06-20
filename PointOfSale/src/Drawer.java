import java.math.BigDecimal;

public class Drawer
{
    private final String paymentMethodAbcCode;
    private final BigDecimal startingBalance;
    private BigDecimal balance;
    private BigDecimal cashIn;
    private BigDecimal cashOut;

    public Drawer( String paymentMethodAbcCode, BigDecimal startingBalance )
    {
        this.paymentMethodAbcCode = paymentMethodAbcCode;
        this.startingBalance = startingBalance;
        setBalance( getStartingBalance() );
        setCashIn( BigDecimal.ZERO );
        setCashOut( BigDecimal.ZERO );
    }

    public void update( PaymentDetail paymentDetail )
    {
        setCashIn( getCashIn().add( paymentDetail.getPayment() ) );
        setCashOut( getCashOut().add( paymentDetail.getChange() ) );
        setBalance( getBalance().add( paymentDetail.getCost() ) );
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    private void setBalance( BigDecimal balance )
    {
        this.balance = balance;
    }

    public BigDecimal getCashIn()
    {
        return cashIn;
    }

    private void setCashIn( BigDecimal cashIn )
    {
        this.cashIn = cashIn;
    }

    public BigDecimal getCashOut()
    {
        return cashOut;
    }

    private void setCashOut( BigDecimal cashOut )
    {
        this.cashOut = cashOut;
    }

    public BigDecimal getStartingBalance()
    {
        return startingBalance;
    }

    public String getPaymentMethodAbcCode()
    {
        return paymentMethodAbcCode;
    }
}