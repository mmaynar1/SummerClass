import java.math.BigDecimal;

public class SaleItemReportDetail
{
    private final String inventoryItemName;
    private int saleItemCount;
    private final BigDecimal unitPrice;
    private BigDecimal extendedPrice;
    private BigDecimal tax;
    private final BigDecimal taxRate;

    public SaleItemReportDetail( String inventoryItemName, int saleItemCount, BigDecimal unitPrice, BigDecimal extendedPrice, BigDecimal tax, BigDecimal taxRate )

    {
        this.inventoryItemName = inventoryItemName;
        setSaleItemCount( saleItemCount );
        setExtendedPrice( extendedPrice );
        this.unitPrice = unitPrice;
        this.tax = tax;
        this.taxRate = taxRate;
    }

    public SaleItemReportDetail( String inventoryItemName, BigDecimal unitPrice, BigDecimal extendedPrice, BigDecimal tax, BigDecimal taxRate )
    {
        this( inventoryItemName, 1, unitPrice, extendedPrice, tax, taxRate );
    }

    public String getInventoryItemName()
    {
        return inventoryItemName;
    }

    public int getSaleItemCount()
    {
        return saleItemCount;
    }

    private void setSaleItemCount( int saleItemCount )
    {
        this.saleItemCount = saleItemCount;
    }

    public BigDecimal getExtendedPrice()
    {
        return extendedPrice;
    }

    private void setExtendedPrice( BigDecimal extendedPrice )
    {
        this.extendedPrice = extendedPrice;
    }

    public void update( SaleItem saleItem )
    {
        incrementSaleItemCount( saleItem );
        setExtendedPrice( getExtendedPrice().add( saleItem.getExtendedPrice() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP ) );
        setTax( getTax().add( saleItem.getTax() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP ) );
    }

    private void incrementSaleItemCount( SaleItem saleItem )
    {
        setSaleItemCount( getSaleItemCount() + saleItem.getQuantity() );
    }

    public BigDecimal getTax()
    {
        return tax;
    }

    private void setTax( BigDecimal tax )
    {
        this.tax = tax;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public BigDecimal getTaxRate()
    {
        return taxRate;
    }
}
