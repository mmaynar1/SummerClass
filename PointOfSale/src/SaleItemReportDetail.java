import java.math.BigDecimal;

public class SaleItemReportDetail
{
    private final String inventoryItemName;
    private final String clubId;
    private int saleItemCount;
    private final BigDecimal unitPrice;
    private BigDecimal extendedPrice;
    private BigDecimal discount;
    private BigDecimal subTotal;
    private BigDecimal tax;
    private final BigDecimal taxRate;

    public SaleItemReportDetail( String clubId, String inventoryItemName, int saleItemCount, BigDecimal unitPrice, BigDecimal extendedPrice, BigDecimal discount, BigDecimal subTotal, BigDecimal tax, BigDecimal taxRate )

    {
        this.inventoryItemName = inventoryItemName;
        this.clubId = clubId;
        setSaleItemCount( saleItemCount );
        setExtendedPrice( extendedPrice );
        this.unitPrice = unitPrice;
        setDiscount( discount );
        setSubTotal( subTotal );
        setTax( tax );
        this.taxRate = taxRate;
    }

    public SaleItemReportDetail( String clubId,String inventoryItemName, BigDecimal unitPrice, BigDecimal extendedPrice, BigDecimal discount, BigDecimal subTotal, BigDecimal tax, BigDecimal taxRate )
    {
        this( clubId, inventoryItemName, 1, unitPrice, extendedPrice, discount, subTotal, tax, taxRate );
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

    public BigDecimal getTotal()
    {
        return getSubTotal().add( getTax() );
    }


    public void update( SaleItem saleItem )
    {
        incrementSaleItemCount( saleItem );
        setExtendedPrice( getExtendedPrice().add( saleItem.getExtendedPrice() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP ) );
        setTax( getTax().add( saleItem.getTax() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP ) );
        setDiscount( getDiscount().add( saleItem.getDiscount() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP ) );
        setSubTotal( getSubTotal().add( saleItem.getSubTotal() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP ));
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

    public BigDecimal getDiscount()
    {
        return discount;
    }

    private void setDiscount( BigDecimal discount )
    {
        this.discount = discount;
    }

    public BigDecimal getSubTotal()
    {
        return subTotal;
    }

    private void setSubTotal( BigDecimal subTotal )
    {
        this.subTotal = subTotal;
    }

    public String getClubId()
    {
        return clubId;
    }
}