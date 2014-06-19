import java.math.BigDecimal;

public class Drawer
{
    public static final BigDecimal STARTING_BALANCE = new BigDecimal( 500 );

    private BigDecimal balance;
    private BigDecimal cashIn;
    private BigDecimal cashOut;

    public Drawer()
    {
        setBalance( STARTING_BALANCE );
        setCashIn( BigDecimal.ZERO );
        setCashOut( BigDecimal.ZERO );
    }

    public void updateBalance( Sale sale )
    {
        for (PaymentDetail paymentDetail : sale.getPaymentDetails())
        {
            if ( paymentDetail.getPaymentMethod() == PaymentMethod.CASH )
            {
                setCashIn( getCashIn().add( paymentDetail.getPayment() ) );
                setCashOut( getCashOut().add( paymentDetail.getChange() ) );
                setBalance( getBalance().add( paymentDetail.getCost() ) );
            }
        }
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
}
