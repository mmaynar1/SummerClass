public class Statistic
{
    public static final int STARTING_BALANCE = 500;
    public static final int BET = 10;

    private int balance;
    private int greatestBalance;

    private int wonComingOut;
    private int lostComingOut;
    private int madePoint;
    private int lostPoint;
    private int totalWinnings;
    private int totalLosses;
    private int betsPlaced;

    Statistic()
    {
        this( STARTING_BALANCE );
    }

    Statistic( int balance )
    {
        setBalance( balance );
        setGreatestBalance( balance );
    }

    public void print()
    {
        System.out.println( "Current Balance: " + getBalance() );
        System.out.println( "Wins Coming Out: " + getWonComingOut() );
        System.out.println( "Losses Coming Out: " + getLostComingOut() );
        System.out.println( "Made Point: " + getMadePoint() );
        System.out.println( "Lost Point: " + getLostPoint() );
        System.out.println( "Greatest Balance: " + getGreatestBalance() );
        System.out.println( "Total Winnings: " + getTotalWinnings() );
        System.out.println( "Total Losses: " + getTotalLosses() );
        System.out.println( "Total Bets Placed: " + getBetsPlaced() );
    }

    public void updateWonComingOut()
    {
        addWinningsToBalance();
        incrementWonComingOut();
        addToTotalWinnings();
        updateGreatestBalance();
        incrementBets();
    }

    public void updateLostComingOut()
    {
        subtractLossesFromBalance();
        incrementLostComingOut();
        addToTotalLosses();
        incrementBets();
    }

    public void updateMadePoint()
    {
        addWinningsToBalance();
        incrementMadePoint();
        addToTotalWinnings();
        updateGreatestBalance();
        incrementBets();
    }

    public void updateLostPoint()
    {
        subtractLossesFromBalance();
        incrementLostPoint();
        addToTotalLosses();
        incrementBets();
    }

    private void incrementBets()
    {
        setBetsPlaced( getBetsPlaced() + 1 );
    }

    private void addWinningsToBalance()
    {
        setBalance( getBalance() + BET );
    }

    private void subtractLossesFromBalance()
    {
        setBalance( getBalance() - BET );
    }

    private void incrementWonComingOut()
    {
        setWonComingOut( getWonComingOut() + 1 );
    }

    private void addToTotalWinnings()
    {
        setTotalWinnings( getTotalWinnings() + BET );
    }

    private void updateGreatestBalance()
    {
        if ( getGreatestBalance() < getBalance() )
        {
            setGreatestBalance( getBalance() );
        }
    }

    private void incrementLostComingOut()
    {
        setLostComingOut( getLostComingOut() + 1 );
    }

    private void addToTotalLosses()
    {
        setTotalLosses( getTotalLosses() - BET );
    }

    private void incrementMadePoint()
    {
        setMadePoint( getMadePoint() + 1 );
    }

    private void incrementLostPoint()
    {
        setLostPoint( getLostPoint() + 1 );
    }

    public int getWonComingOut()
    {
        return wonComingOut;
    }

    private void setWonComingOut( int wonComingOut )
    {
        this.wonComingOut = wonComingOut;
    }

    public int getLostComingOut()
    {
        return lostComingOut;
    }

    private void setLostComingOut( int lostComingOut )
    {
        this.lostComingOut = lostComingOut;
    }

    public int getMadePoint()
    {
        return madePoint;
    }

    private void setMadePoint( int madePoint )
    {
        this.madePoint = madePoint;
    }

    public int getGreatestBalance()
    {
        return greatestBalance;
    }

    private void setGreatestBalance( int greatestBalance )
    {
        this.greatestBalance = greatestBalance;
    }

    public int getTotalWinnings()
    {
        return totalWinnings;
    }

    private void setTotalWinnings( int totalWinnings )
    {
        this.totalWinnings = totalWinnings;
    }

    public int getTotalLosses()
    {
        return totalLosses;
    }

    private void setTotalLosses( int totalLosses )
    {
        this.totalLosses = totalLosses;
    }

    public int getBetsPlaced()
    {
        return betsPlaced;
    }

    private void setBetsPlaced( int betsPlaced )
    {
        this.betsPlaced = betsPlaced;
    }

    public int getBalance()
    {
        return balance;
    }

    private void setBalance( int balance )
    {
        this.balance = balance;
    }

    public int getLostPoint()
    {
        return lostPoint;
    }

    public void setLostPoint( int lostPoint )
    {
        this.lostPoint = lostPoint;
    }
}