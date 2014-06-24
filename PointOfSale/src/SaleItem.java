import java.math.BigDecimal;

public class SaleItem
{
   public static final int MONEY_DECIMAL_PLACES = 2;

    private final String inventoryItemId;
    private final int quantity;
    private final String id;
    private final BigDecimal discountRate;

    public SaleItem( String inventoryItemId, int quantity, BigDecimal discountRate )
    {
        this.inventoryItemId = inventoryItemId;
        this.quantity = quantity;
        this.id = RandomGenerator.getGuid();
        this.discountRate = discountRate;
    }

    public BigDecimal getTotal()
    {
        return getSubTotal().add( getTax() ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
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
        return getSubTotal().multiply( getTaxRate() ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
    }

    public BigDecimal getSubTotal()
    {
        return getExtendedPrice().subtract( getDiscount() ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
    }

    public BigDecimal getDiscount()
    {
        return getExtendedPrice().multiply( getDiscountRate() ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
    }

    public BigDecimal getExtendedPrice()
    {
        BigDecimal unitPrice = Database.getUnitPrice( getInventoryItemId() );
        return (unitPrice.multiply( new BigDecimal( getQuantity() ) ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP ));
    }

    public String getInventoryItemId()
    {
        return inventoryItemId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public String getId()
    {
        return id;
    }

    private BigDecimal getDiscountRate()
    {
        return discountRate;
    }
}
