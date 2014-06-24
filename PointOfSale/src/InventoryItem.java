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
        this(name,unitPrice,tax,RandomGenerator.getGuid());
    }

    public InventoryItem( String name, BigDecimal unitPrice, Tax tax, String id )
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.id = id;
        this.tax = tax;
    }

    public InventoryItem (InventoryItem inventoryItem)
    {
        this(inventoryItem.getName(), inventoryItem.getUnitPrice(), inventoryItem.getTax(), inventoryItem.getId());
    }


    public InventoryItem (String id)
    {
        this(Database.getInventoryItem( id ));
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