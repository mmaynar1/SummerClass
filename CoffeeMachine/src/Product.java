public class Product
{
    private final Ingredients recipe;
    private final double price;
    private final String name;

    public Product( String name, double price, Ingredients recipe )
    {
        this.name = name;
        this.price = price;
        this.recipe = recipe;
    }

    public Ingredients getRecipe()
    {
        return recipe;
    }

    public double getPrice()
    {
        return price;
    }

    public String getName()
    {
        return name;
    }
}