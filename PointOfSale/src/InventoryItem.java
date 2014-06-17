import java.math.BigDecimal;

public final class InventoryItem
{
    private final String name;
    private final BigDecimal unitPrice;
    private final String id;

    public InventoryItem( String name, double unitPrice )
    {
        this(name, new BigDecimal( unitPrice ));
    }

    public InventoryItem( String name, BigDecimal unitPrice )
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.id = RandomGenerator.getGuid();
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
}