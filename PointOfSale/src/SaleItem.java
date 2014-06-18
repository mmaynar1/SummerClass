import java.math.BigDecimal;

public class SaleItem
{
    private final String inventoryItemId;
    private final int quantity;

    public SaleItem( String inventoryItemId, int quantity )
    {
        this.inventoryItemId = inventoryItemId;
        this.quantity = quantity;
    }

    public String getInventoryItemName()
    {
        return Database.getInventoryItemName( getInventoryItemId() );
    }

    public BigDecimal getTaxRate()
    {
        return Database.getInventoryItemTaxRate( getInventoryItemId() );
    }

    public BigDecimal getTax()
    {
        return getExtendedPrice().multiply( getTaxRate() );
    }



    public BigDecimal getExtendedPrice()
    {
        BigDecimal unitPrice = Database.getUnitPrice( getInventoryItemId() );
        return (unitPrice.multiply( new BigDecimal( getQuantity() ) ));
    }

    public String getInventoryItemId()
    {
        return inventoryItemId;
    }

    public int getQuantity()
    {
        return quantity;
    }
}
