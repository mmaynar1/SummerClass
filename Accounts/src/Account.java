import java.math.BigDecimal;
import java.text.DecimalFormat;

public abstract class Account
{
    private String memberName;
    public String accountNumber;
    private BigDecimal balance;

    public abstract String getType();

    public abstract void setAccountNumber( String accountNumber );

    public Account( String memberName, String accountNumber, BigDecimal balance )
    {
        this.memberName = memberName;
        setAccountNumber( accountNumber );
        setBalance( balance );
    }

    public void prepareDocuments()
    {

    }

    public void collectServiceFee()
    {

    }

    public void payInterest()
    {
        setBalance( getBalance().add( BigDecimal.ONE ) );
    }

    public final void addInterest( BigDecimal interestRate )
    {
        BigDecimal interest = getBalance().multiply( interestRate );
        setBalance( getBalance().add( interest ) );
    }

    public String toString()
    {
        return "Type: " + getType() + "\n" +
               "Name: " + getMemberName() + "\n" +
               "Account Number: " + getAccountNumber() + "\n" +
               "Balance: " + formatMoney( getBalance() ) + "\n";
    }

    private String formatMoney( BigDecimal money )
    {
        String formattedMoney;

        if ( money == null )
        {
            formattedMoney = "$0.00";
        }
        else
        {
            final DecimalFormat moneyFormat = new DecimalFormat( "$#,##0.00" );
            formattedMoney = moneyFormat.format( money );
        }

        return formattedMoney;
    }

    public final String getMemberName()
    {
        return memberName;
    }

    public final String getAccountNumber()
    {
        return accountNumber;
    }

    public final BigDecimal getBalance()
    {
        return balance;
    }

    public final void setBalance( BigDecimal balance )
    {
        this.balance = balance;
    }
}