import java.text.DecimalFormat;

public class TestCraps
{
    public static void main( String[] args )
    {
        TestCraps craps = new TestCraps();
        craps.run();
    }

    public void run()
    {
        CrapsGame crapsGame = new CrapsGame();

        final int NUMBER_OF_SIMULATIONS = 1000;
        StatisticsCollection statistics = new StatisticsCollection();

        for (int i = 0; i < NUMBER_OF_SIMULATIONS; i++)
        {
            crapsGame.runSimulation();
            statistics.add( crapsGame.getStatistic() );
        }

        printStatistics( statistics );

    }

    private void printStatistics( StatisticsCollection statistics )
    {
        //Running totals
        int balance = 0;
        int runningGreatestBalance = 0;
        int wonComingOut = 0;
        int lostComingOut = 0;
        int madePoint = 0;
        int lostPoint = 0;
        int totalWinnings = 0;
        int totalLosses = 0;
        int betsPlaced = 0;

        int greatestBalanceForEachGame = 0;

        int doubledMoney = 0;
        int lostItAll = 0;

        int numberOfGames = statistics.getSize();

        for (int i = 0; i < numberOfGames; ++i)
        {
            balance += statistics.getValue( i ).getBalance();
            runningGreatestBalance += statistics.getValue( i ).getGreatestBalance();
            wonComingOut += statistics.getValue( i ).getWonComingOut();
            lostComingOut += statistics.getValue( i ).getLostComingOut();
            madePoint += statistics.getValue( i ).getMadePoint();
            lostPoint += statistics.getValue( i ).getLostPoint();
            totalWinnings += statistics.getValue( i ).getTotalWinnings();
            totalLosses += statistics.getValue( i ).getTotalLosses();
            betsPlaced += statistics.getValue( i ).getBetsPlaced();


            greatestBalanceForEachGame = statistics.getValue( i ).getGreatestBalance();
            if ( greatestBalanceForEachGame == CrapsGame.MAX_BALANCE )
            {
                ++doubledMoney;
            }
            else
            {
                ++lostItAll;
            }
        }

        DecimalFormat truncate = new DecimalFormat( "0.00" );

        System.out.println( "---------- Craps Averages ----------" );
        System.out.println( "Current Balance: " + truncate.format( ((double) balance / numberOfGames) ) );
        System.out.println( "Wins Coming Out: " + truncate.format( ((double) wonComingOut / numberOfGames) ) );
        System.out.println( "Losses Coming Out: " + truncate.format( ((double) lostComingOut / numberOfGames) ) );
        System.out.println( "Made Point: " + truncate.format( ((double) madePoint / numberOfGames) ) );
        System.out.println( "Lost Point: " + truncate.format( ((double) lostPoint / numberOfGames) ) );
        System.out.println( "Greatest Balance: " + truncate.format( ((double) runningGreatestBalance / numberOfGames) ) );
        System.out.println( "Total Winnings: " + truncate.format( ((double) totalWinnings / numberOfGames) ) );
        System.out.println( "Total Losses: " + truncate.format( ((double) totalLosses / numberOfGames) ) );
        System.out.println( "Total Bets Placed: " + truncate.format( ((double) betsPlaced / numberOfGames) ) );
        System.out.println();
        System.out.println( "Doubled Money: " + doubledMoney + " times" );
        System.out.println( "Lost It All: " + lostItAll + " times" );
        System.out.println( "Winning Percentage: " + truncate.format( (double) 100 * doubledMoney / numberOfGames ) + "%" );

    }
}