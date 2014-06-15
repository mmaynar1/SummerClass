public class TestAccounts
{

    public static void main( String[] args )
    {
        new TestAccounts().go();
    }

    private void go()
    {
        final int MAX_ACCOUNTS = 10;
        Account[] accounts = new Account[MAX_ACCOUNTS];

        createAccounts( accounts );  // fill in array with random Savings/Checking instances

        collectServiceCharges( accounts );   // collects service charges on each account

        payInterest( accounts );   // pays interest on each account

        printAccounts( accounts );  // print each account

        prepareDocuments( accounts );   // prepares documents on each account*/
    }

    private void prepareDocuments( Account[] accounts )
    {
        for (Account account : accounts)
        {
            account.prepareDocuments();
        }
    }

    private void printAccounts( Account[] accounts )
    {
        for (Account account : accounts)
        {
            System.out.println( account );
        }
        System.out.println( "***************************************" );
    }

    private void payInterest( Account[] accounts )
    {
        for (Account account : accounts)
        {
            account.payInterest();
        }
    }

    private void collectServiceCharges( Account[] accounts )
    {
        for (Account account : accounts)
        {
            account.collectServiceFee();
        }
    }

    private void createAccounts( Account[] accounts )
    {
        for (int i = 0; i < accounts.length; i++)
        {
            int random = RandomGenerator.getInt( 0, 2 );
            if ( random == 1 )
            {
                accounts[i] = new CheckingAccount( RandomGenerator.getName(), RandomGenerator.getString( 10 ), RandomGenerator.getMoney() );
            }
            else
            {
                accounts[i] = new SavingsAccount( RandomGenerator.getName(), RandomGenerator.getGuid(), RandomGenerator.getMoney() );
            }
        }
    }

}