public class CrapsGame
{
    public static final int MIN_BALANCE = 10;
    public static final int MAX_BALANCE = 1000;

    private Statistic statistic;

    public enum ResultOfRoll
    {
        wonComingOut, lostComingOut, madePoint, lostPoint, setThePoint, nothing
    }

    public CrapsGame()
    {
        setStatistic( new Statistic() );
    }

    public void runSimulation()
    {
        int betCount = 1;
        while ( getStatistic().getBalance() >= MIN_BALANCE && getStatistic().getBalance() < MAX_BALANCE )
        {
            playBet();

            //Uncomment to view results of each bet
/*            System.out.println( "----------Bet " + betCount + "----------" );
            getStatistic().print();
            System.out.println();*/

            ++betCount;
        }

        reset();
    }

    private void playBet()
    {
        ResultOfRoll resultOfRoll;

        int rollCount = 1;
        boolean turnIsOver = false;
        int point = -1;
        while ( !turnIsOver )
        {
            int diceValue = rollDice();
            resultOfRoll = getResultOfRoll( rollCount, diceValue, point );

            if(resultOfRoll == ResultOfRoll.setThePoint)
            {
                point = diceValue;
            }


            updateStatistics( resultOfRoll );

            if ( resultOfRoll == ResultOfRoll.wonComingOut ||
                 resultOfRoll == ResultOfRoll.lostComingOut ||
                 resultOfRoll == ResultOfRoll.madePoint ||
                 resultOfRoll == ResultOfRoll.lostPoint )
            {
                turnIsOver = true;
            }

            ++rollCount;
        }
    }

    private void updateStatistics( ResultOfRoll resultOfRoll )
    {
        switch ( resultOfRoll )
        {
            case wonComingOut:
                getStatistic().updateWonComingOut();
                break;
            case lostComingOut:
                getStatistic().updateLostComingOut();
                break;
            case madePoint:
                getStatistic().updateMadePoint();
                break;
            case lostPoint:
                getStatistic().updateLostPoint();
                break;
            case setThePoint:
            case nothing:
                //Nothing needs to be updated
                break;
            default:
                throw new RuntimeException( "Invalid ResultOfRoll" );
        }
    }

    private void reset()
    {
        setStatistic( new Statistic() );
    }

    private ResultOfRoll getResultOfRoll( int rollNumber, int diceValue, int point )
    {
        ResultOfRoll resultOfRoll;

        if ( rollNumber == 1 )
        {
            if ( diceValue == 7 || diceValue == 11 )
            {
                resultOfRoll = ResultOfRoll.wonComingOut;
            }
            else if ( diceValue == 2 || diceValue == 3 || diceValue == 12 )
            {
                resultOfRoll = ResultOfRoll.lostComingOut;
            }
            else
            {
                resultOfRoll = ResultOfRoll.setThePoint;
            }
        }
        else
        {
            if ( point == diceValue )
            {
                resultOfRoll = ResultOfRoll.madePoint;
            }
            else if ( diceValue == 7 )
            {
                resultOfRoll = ResultOfRoll.lostPoint;
            }
            else
            {
                resultOfRoll = ResultOfRoll.nothing;
            }
        }

        return resultOfRoll;
    }

    private int rollDice()
    {
        final int DICE_VALUE = 6;
        return getRandom( DICE_VALUE ) + getRandom( DICE_VALUE );
    }

    private int getRandom( int range )
    {
        return 1 + (int) (Math.random() * range);
    }

    public Statistic getStatistic()
    {
        return statistic;
    }

    private void setStatistic( Statistic statistic )
    {
        this.statistic = statistic;
    }
}