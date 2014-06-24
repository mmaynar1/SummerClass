import java.math.BigDecimal;

public class SaleItem
{
    public static final int MONEY_DECIMAL_PLACES = 2;
    public static final String DELIMITER = "!";

    private final String inventoryItemId;
    private final int quantity;
    private final String id;
    private final BigDecimal discountRate;

    public SaleItem( String inventoryItemId, int quantity, BigDecimal discountRate )
    {
        this( inventoryItemId, quantity, discountRate, RandomGenerator.getGuid() );
    }

    public SaleItem( String inventoryItemId, int quantity, BigDecimal discountRate, String id )
    {
        this.inventoryItemId = inventoryItemId;
        this.quantity = quantity;
        this.id = id;
        this.discountRate = discountRate;
    }

    public String getTextRepresentation()
    {
        return DELIMITER + getInventoryItemId() + DELIMITER +
               getQuantity() + DELIMITER +
               getId() + DELIMITER +
               Format.formatRate( getDiscountRate());
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
