import java.math.BigDecimal;

public class SaleItemReportDetail
{
    private final String saleItem;
    private int saleItemCount;
    private BigDecimal total;

    public SaleItemReportDetail( String saleItem, int saleItemCount, BigDecimal total )
    {
        this.saleItem = saleItem;
        setSaleItemCount( saleItemCount );
        setTotal( total );
    }

    public SaleItemReportDetail( String saleItem, BigDecimal unitPrice )
    {
        this( saleItem, 1, unitPrice );
    }

    public String getSaleItem()
    {
        return saleItem;
    }

    public int getSaleItemCount()
    {
        return saleItemCount;
    }

    private void setSaleItemCount( int saleItemCount )
    {
        this.saleItemCount = saleItemCount;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    private void setTotal( BigDecimal total )
    {
        this.total = total;
    }

    public void update( InventoryItem inventoryItem )
    {
        incrementSaleItemCount();
        setTotal( getTotal().add( inventoryItem.getUnitPrice() ) );
    }

    private void incrementSaleItemCount()
    {
        setSaleItemCount( getSaleItemCount() + 1 );
    }
}
