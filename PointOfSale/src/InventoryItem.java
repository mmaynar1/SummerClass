import java.math.BigDecimal;

public final class InventoryItem
{
    private final String name;
    private final BigDecimal unitPrice;
    private final String id;
    private final Tax tax;


    public InventoryItem( String name, double unitPrice, Tax tax )
    {
        this(name, new BigDecimal( unitPrice ), tax);
    }

    public InventoryItem( String name, BigDecimal unitPrice, Tax tax )
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.id = RandomGenerator.getGuid();
        this.tax = tax;
    }

    public String getName()
    {
        return name;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public String getId()
    {
        return id;
    }

    public Tax getTax()
    {
        return tax;
    }
}