public class Machine
{
    public static final int FULLY_STOCKED = 2;
    public static final double DRINK_PRICE = .35;

    private Ingredients inventory;
    private MoneyProcessor moneyProcessor;
    private Product black;
    private Product white;
    private Product blackSweet;
    private Product whiteSweet;

    public enum Buttons
    {
        orderBlack( "Black Coffee" ), payQuarter("Insert Quarter");

        private String label;

        Buttons( String label )
        {
            this.label = label;
        }
    }

    public Machine()
    {
        this.inventory = new Ingredients( FULLY_STOCKED );
        this.moneyProcessor = new MoneyProcessor();
        this.black = new Product( "Black", DRINK_PRICE, new Ingredients( 1, 1, 1, 0, 0 ) );
    }

    public void getCommand( Buttons button )
    {
        switch ( button )
        {
            case orderBlack:
                order( getBlack() );
                break;
            case payQuarter:
                getMoneyProcessor().addQuarter();
                break;
        }
    }

    public void order( Product product )
    {
        if ( getMoneyProcessor().haveEnoughMoney( product.getPrice() ) && decrementInventory( product ) )
        {
            dispense( product );
            getMoneyProcessor().decrementTotal( product.getPrice() );
        }
        else
        {
            System.out.println("suck");
        }
    }

    private void dispense( Product product )
    {
        System.out.println( product.getName() );
    }

    private boolean decrementInventory( Product purchasedProduct )
    {
        boolean updated = false;
        Ingredients recipe = purchasedProduct.getRecipe();
        if ( getInventory().tryToDecrementCup( recipe.getCup() ) &&
             getInventory().tryToDecrementCoffee( recipe.getCoffee() ) &&
             getInventory().tryToDecrementWater( recipe.getWater() ) &&
             getInventory().tryToDecrementSugar( recipe.getSugar() ) &&
             getInventory().tryToDecrementCream( recipe.getCream() ) )
        {
            updated = true;
        }
        return updated;
    }

    private Ingredients getInventory()
    {
        return inventory;
    }

    private MoneyProcessor getMoneyProcessor()
    {
        return moneyProcessor;
    }

    //todo make private
    public Product getBlack()
    {
        return black;
    }

    private Product getWhite()
    {
        return white;
    }

    private Product getBlackSweet()
    {
        return blackSweet;
    }

    private Product getWhiteSweet()
    {
        return whiteSweet;
    }
}