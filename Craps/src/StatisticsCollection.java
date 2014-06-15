public class StatisticsCollection
{
    public static void main( String[] args )
    {
        StatisticsCollection statisticsCollection = new StatisticsCollection();
        statisticsCollection.test();
    }

    public static final int DEFAULT_SIZE = 10;
    public static final int DEFAULT_EXPANSION = DEFAULT_SIZE;

    private Statistic[] statistics;
    private int first;
    private int last;

    public StatisticsCollection()
    {
        this( DEFAULT_SIZE );
    }

    public StatisticsCollection( int size )
    {
        setStatistics( size );
        setFirst( 0 );
        setLast( -1 );
    }

    public int getInternalSize()
    {
        return getStatistics().length;
    }

    public int getSize()
    {
        return (getLast() - getFirst() + 1);
    }

    public void add( Statistic statistic )
    {
        incrementLast();
        if ( (getLast() - getFirst()) == getInternalSize() )
        {
            expand();
        }
        setLastValue( statistic );
    }

    private void expand()
    {
        Statistic[] expandedStatistics = new Statistic[getInternalSize() + DEFAULT_EXPANSION];

        for (int i = 0; i < getInternalSize(); ++i)
        {
            expandedStatistics[i] = getStatistics()[i];
        }

        setStatistics( expandedStatistics );
    }

    public Statistic[] getValues()
    {
        optimize();
        return getStatistics().clone();
    }

    public Statistic pop()
    {
        Statistic poppedValue = peek();

        Statistic emptyStatistic = null;
        setFirstValue( emptyStatistic );
        incrementFirst();

        if ( getFirst() > getLast() )
        {
            expand();
        }

        return poppedValue;
    }


    public Statistic peek()
    {
        if ( getStatistics()[getFirst()] != null )
        {
            return getStatistics()[getFirst()];
        }
        else
        {
            throw new RuntimeException( "First element is NULL" );
        }
    }

    public Statistic getValue( int index )
    {
        index = adjustIndex( index );
        if ( index < getFirst() || index > getLast() )
        {
            throw new RuntimeException( "Index " + index + " is out of bounds" );
        }
        else
        {
            return getStatistics()[index];
        }
    }

    public void setFirstValue( Statistic statistic )
    {
        if ( getFirst() > getLast() )
        {
            throw new RuntimeException( "Error: First pointer is greater than last pointer" );
        }
        else
        {
            getStatistics()[getFirst()] = statistic;
        }
    }

    public void setLastValue( Statistic statistic )
    {
        if ( getFirst() > getLast() )
        {
            throw new RuntimeException( "Error: First pointer is greater than last pointer" );
        }
        else
        {
            getStatistics()[getLast()] = statistic;
        }
    }

    private void setValue( int index, Statistic statistic )
    {
        index = adjustIndex( index );
        if ( index < getFirst() || index > getLast() )
        {
            throw new RuntimeException( "Index " + index + " is out of bounds" );
        }
        else
        {
            getStatistics()[index] = statistic;
        }
    }

    private int adjustIndex( int index )
    {
        return index + getFirst();
    }

    private void optimize()
    {
        int emptyStatisticCount = 0;
        for (int i = 0; i < getInternalSize(); i++)
        {
            if ( getStatistics()[i] == null )
            {
                ++emptyStatisticCount;
            }
        }

        int newSize = getInternalSize() - emptyStatisticCount;

        Statistic[] statistics = new Statistic[newSize];

        if ( newSize == 0 )
        {
            clear();
        }
        else
        {
            transferElements( statistics );

            setFirst( 0 );
            setLast( statistics.length - 1 );

            setStatistics( statistics );
        }

    }

    private void transferElements( Statistic[] newStatistics )
    {
        for (int oldElements = 0; oldElements < getInternalSize(); oldElements++)
        {
            for (int newElements = 0; newElements < newStatistics.length; newElements++)
            {
                if ( getStatistics()[oldElements] != null )
                {
                    newStatistics[newElements] = getStatistics()[oldElements];
                }
            }
        }
    }

    private void clear()
    {
        setStatistics( 0 );
        setFirst( 0 );
        setLast( -1 );
    }

    private void incrementFirst()
    {
        setFirst( getFirst() + 1 );
    }

    private void incrementLast()
    {
        setLast( getLast() + 1 );
    }

    private void test()
    {
        Statistic stat1 = new Statistic( 12 );
        Statistic stat2 = new Statistic( 13 );
        StatisticsCollection statisticsCollection = new StatisticsCollection();

        //Size should be 0 upon creation
        assert (statisticsCollection.getSize() == 0);

        //Internal Size should be 10
        assert (statisticsCollection.getInternalSize() == 10);

        //Add one, and size should be 1 and element should be correct
        statisticsCollection.add( stat1 );
        assert (statisticsCollection.getSize() == 1);
        assert (statisticsCollection.getValue( 0 ).getBalance() == stat1.getBalance());
        assert (statisticsCollection.getValue( 0 ).getBalance() == 12);

        //Test peek
        assert (statisticsCollection.peek().getBalance() == stat1.getBalance());

        //Add another and verify
        statisticsCollection.add( stat2 );
        assert (statisticsCollection.getSize() == 2);
        assert (statisticsCollection.getValue( 1 ).getBalance() == stat2.getBalance());

        //Test setValue
        statisticsCollection.setValue( 0, stat2 );
        assert (statisticsCollection.getSize() == 2);
        assert (statisticsCollection.getValue( 0 ).getBalance() == stat2.getBalance());
        assert (statisticsCollection.getValue( 0 ).getBalance() == 13);

        //Test optimize
        statisticsCollection.optimize();
        assert (statisticsCollection.getInternalSize() == statisticsCollection.getSize());
        assert (statisticsCollection.getValue( 0 ).getBalance() == stat2.getBalance());
        assert (statisticsCollection.getValue( 1 ).getBalance() == stat2.getBalance());

        //Test expand
        for (int i = 0; i < 7; i++)
        {
            statisticsCollection.add( stat1 );
        }
        assert (statisticsCollection.getSize() == 9);
        assert (statisticsCollection.getInternalSize() == 12);
        assert (statisticsCollection.getFirst() == 0);
        assert (statisticsCollection.getLast() == 8);

        //Test pop
        statisticsCollection.pop();
        statisticsCollection.pop();
        statisticsCollection.pop();
        statisticsCollection.pop();
        statisticsCollection.pop();
        statisticsCollection.pop();
        statisticsCollection.pop();

        statisticsCollection.setValue( 0, stat2 );
        assert (statisticsCollection.getSize() == 2);
        assert (statisticsCollection.getInternalSize() == 12);
        statisticsCollection.pop();
        assert (statisticsCollection.getSize() == 1);
        assert (statisticsCollection.getInternalSize() == 12);
        statisticsCollection.pop();
        assert (statisticsCollection.getSize() == 0);
        assert (statisticsCollection.getInternalSize() == 22);

        //Test getValues()
        statisticsCollection.add( stat1 );
        Statistic[] stats = statisticsCollection.getValues();
        assert (stats[0].getBalance() == 12);

        //Test clear()
        statisticsCollection.clear();
        assert (statisticsCollection.getSize() == 0);
        assert (statisticsCollection.getFirst() == 0);
        assert (statisticsCollection.getLast() == -1);
    }


    private Statistic[] getStatistics()
    {
        return statistics;
    }

    private void setStatistics( int size )
    {
        this.statistics = new Statistic[size];
    }

    private void setStatistics( Statistic[] statistics )
    {
        this.statistics = statistics;
    }

    private int getFirst()
    {
        return first;
    }

    private void setFirst( int first )
    {
        this.first = first;
    }

    private int getLast()
    {
        return last;
    }

    private void setLast( int last )
    {
        this.last = last;
    }
}