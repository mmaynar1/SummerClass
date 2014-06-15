import java.math.BigDecimal;

public class CheckingAccount extends Account
{

    public CheckingAccount( String memberName, String accountNumber, BigDecimal balance )
    {
        super( memberName, accountNumber, balance );
    }

    @Override
    public void prepareDocuments()
    {
        System.out.println( "Order checks" );
    }

    @Override
    public void collectServiceFee()
    {
        BigDecimal serviceFeeMax = new BigDecimal( 100 );
        boolean serviceFeeRequired = getBalance().compareTo( serviceFeeMax ) == -1;

        if ( serviceFeeRequired )
        {
            setBalance( getBalance().subtract( new BigDecimal( 3 ) ) );
        }
    }

    @Override
    public void payInterest()
    {
        BigDecimal interestRate = new BigDecimal( .03 );
        addInterest( interestRate );
    }

    @Override
    public final String getType()
    {
        return "Checking";
    }

    @Override
    public void setAccountNumber( String accountNumber )
    {
        final int ACCOUNT_NUMBER_LENGTH = 10;
        if ( accountNumber.length() == ACCOUNT_NUMBER_LENGTH )
        {
            this.accountNumber = accountNumber;
        }
        else
        {
            throw new RuntimeException( getType() + " account number must be " + ACCOUNT_NUMBER_LENGTH + " characters long." );
        }
    }
}