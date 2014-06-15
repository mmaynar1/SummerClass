import java.math.BigDecimal;

public class SavingsAccount extends Account
{
    public SavingsAccount( String memberName, String accountNumber, BigDecimal balance )
    {
        super( memberName, accountNumber, balance );
    }

    @Override
    public void payInterest()
    {
        if ( getBalance().compareTo( new BigDecimal( 200 ) ) == -1 )
        {
            BigDecimal interestRate = new BigDecimal( .02 );
            addInterest( interestRate );
        }
        else
        {
            BigDecimal interestRate = new BigDecimal( .04 );
            addInterest( interestRate );
        }
    }

    @Override
    public String getType()
    {
        return "Savings";
    }

    @Override
    public void setAccountNumber( String accountNumber )
    {
        final int GUID_LENGTH = 32;
        if ( accountNumber.length() == GUID_LENGTH )
        {
            this.accountNumber = accountNumber;
        }
        else
        {
            throw new RuntimeException( getType() + " account number must be a " + GUID_LENGTH +  " character GUID." );
        }
    }
}
