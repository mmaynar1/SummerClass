public class MoneyProcessor
{
    private double total;

    public MoneyProcessor()
    {
        setTotal( 0 );
    }

    public boolean haveEnoughMoney( double cost )
    {
        return cost <= getTotal();
    }

    public double giveChange()
    {
        double change = getTotal();
        setTotal( 0 );
        return change;
    }

    public void addQuarter()
    {
        setTotal( getTotal() + .25 );
    }

    public void addNickel()
    {
        setTotal( getTotal() + .05 );
    }

    public void decrementTotal( double price )
    {
        setTotal( getTotal() - price );
    }

    private double getTotal()
    {
        return total;
    }

    private void setTotal( double total )
    {
        this.total = total;
    }
}
