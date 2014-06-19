import java.math.BigDecimal;

public class SaleItemReportDetail
{
    private final String inventoryItemName;
    private int saleItemCount;
    private BigDecimal extendedPrice;
    private BigDecimal tax;

    public SaleItemReportDetail( String inventoryItemName, int saleItemCount, BigDecimal extendedPrice, BigDecimal tax )
    {
        this.inventoryItemName = inventoryItemName;
        setSaleItemCount( saleItemCount );
        setExtendedPrice( extendedPrice );
        this.tax = tax;
    }

    public SaleItemReportDetail( String inventoryItemName, BigDecimal unitPrice, BigDecimal tax )
    {
        this( inventoryItemName, 1, unitPrice ,tax);
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
        incrementSaleItemCount(saleItem);
        setExtendedPrice( getExtendedPrice().add( saleItem.getExtendedPrice() ) );
        setTax( getTax().add( saleItem.getTax() ) );
    }

    private void incrementSaleItemCount(SaleItem saleItem)
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
}
