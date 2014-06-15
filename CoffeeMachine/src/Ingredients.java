public class Ingredients
{
    private int cup;
    private int coffee;
    private int water;
    private int sugar;
    private int cream;

    public Ingredients( int cup, int coffee, int water, int sugar, int cream )
    {
        this.cup = cup;
        this.coffee = coffee;
        this.water = water;
        this.sugar = sugar;
        this.cream = cream;
    }

    public Ingredients(int setAll)
    {
        this(setAll,setAll,setAll,setAll,setAll);
    }


    public boolean tryToDecrementCup(int howMany)
    {
        boolean canDecrement = true;
        if ( getCup() >= 0 )
        {
            setCup( getCup() - howMany );
        }
        else
        {
            canDecrement = false;
        }
        return canDecrement;
    }

    public boolean tryToDecrementCoffee(int howMany)
    {
        boolean canDecrement = true;
        if ( getCoffee() >= 0 )
        {
            setCoffee( getCoffee() - howMany );
        }
        else
        {
            canDecrement = false;
        }
        return canDecrement;
    }

    public boolean tryToDecrementWater(int howMany)
    {
        boolean canDecrement = true;
        if ( getWater() >= 0 )
        {
            setWater( getWater() - howMany );
        }
        else
        {
            canDecrement = false;
        }
        return canDecrement;
    }

    public boolean tryToDecrementSugar(int howMany)
    {
        boolean canDecrement = true;
        if ( getSugar() >= 0 )
        {
            setSugar( getSugar() - howMany );
        }
        else
        {
            canDecrement = false;
        }
        return canDecrement;
    }

    public boolean tryToDecrementCream(int howMany)
    {
        boolean canDecrement = true;
        if ( getCream() >= 0 )
        {
            setCream( getCream() - howMany );
        }
        else
        {
            canDecrement = false;
        }
        return canDecrement;
    }

    private void setCup( int cup )
    {
        this.cup = cup;
    }

    private void setCoffee( int coffee )
    {
        this.coffee = coffee;
    }

    private void setWater( int water )
    {
        this.water = water;
    }

    private void setSugar( int sugar )
    {
        this.sugar = sugar;
    }

    private void setCream( int cream )
    {
        this.cream = cream;
    }

    public int getCup()
    {
        return cup;
    }

    public int getCoffee()
    {
        return coffee;
    }

    public int getWater()
    {
        return water;
    }

    public int getSugar()
    {
        return sugar;
    }

    public int getCream()
    {
        return cream;
    }
}
