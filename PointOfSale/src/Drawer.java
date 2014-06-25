import utility.Format;

import java.math.BigDecimal;

public class Drawer
{
    private final String paymentMethodAbcCode;
    private final BigDecimal startingBalance;
    private BigDecimal balance;
    private BigDecimal moneyIn;
    private BigDecimal moneyOut;

    public Drawer( String paymentMethodAbcCode, BigDecimal startingBalance )
    {
        this( paymentMethodAbcCode, startingBalance, startingBalance, BigDecimal.ZERO, BigDecimal.ZERO );
/*        this.paymentMethodAbcCode = paymentMethodAbcCode;
        this.startingBalance = startingBalance;
        setBalance( getStartingBalance() );
        setMoneyIn( BigDecimal.ZERO );
        setMoneyOut( BigDecimal.ZERO );*/
    }

    public Drawer( String paymentMethodAbcCode, BigDecimal startingBalance, BigDecimal balance, BigDecimal moneyIn, BigDecimal moneyOut )
    {
        this.paymentMethodAbcCode = paymentMethodAbcCode;
        this.startingBalance = startingBalance;
        this.balance = balance;
        this.moneyIn = moneyIn;
        this.moneyOut = moneyOut;
    }


    public String getTextRepresentation()
    {
        String delimiter = ":";
        return getPaymentMethodAbcCode() + delimiter +
               Format.formatMoneyNotPretty( getStartingBalance() ) + delimiter +
               Format.formatMoneyNotPretty( getBalance() ) + delimiter +
               Format.formatMoneyNotPretty( getMoneyIn() ) + delimiter +
               Format.formatMoneyNotPretty( getMoneyOut() );
    }

    public void update( PaymentDetail paymentDetail )
    {
        setMoneyIn( getMoneyIn().add( paymentDetail.getPayment() ) );
        setMoneyOut( getMoneyOut().add( paymentDetail.getChange() ) );
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

    public BigDecimal getMoneyIn()
    {
        return moneyIn;
    }

    private void setMoneyIn( BigDecimal moneyIn )
    {
        this.moneyIn = moneyIn;
    }

    public BigDecimal getMoneyOut()
    {
        return moneyOut;
    }

    private void setMoneyOut( BigDecimal moneyOut )
    {
        this.moneyOut = moneyOut;
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