import java.math.BigDecimal;
import java.util.List;

public final class Sale
{
    private final String memberId;
    private final List<SaleItem> saleItems;
    private final List<PaymentDetail> paymentDetails;
    private final String id;

    public Sale( String memberId,  List<SaleItem> saleItems, List<PaymentDetail> paymentDetails )
    {
        this.memberId = memberId;
        this.saleItems = saleItems;
        this.paymentDetails = paymentDetails;
        this.id = RandomGenerator.getGuid();
    }

    public Sale( Sale sale )
    {
        this( sale.getMemberId(), sale.getSaleItems(), sale.getPaymentDetails() );
    }

    @Override
    public String toString()
    {
        String output = "Member: " + getMemberName() + " " + getMemberId() + "\n";
        output += "Items:\n";
        for (SaleItem saleItem : getSaleItems())
        {
            output += "\tName: " + saleItem.getInventoryItemName() +
                      " Id: " + saleItem.getInventoryItemId() +
                      " Ext Price: " + Format.formatMoney( saleItem.getExtendedPrice()) +
                      " Quantity: " + saleItem.getQuantity() +
                      " Tax Rate: " + Format.formatPercentage( saleItem.getTaxRate() )+
                      " Tax: " + Format.formatMoney(  saleItem.getTax()) + "\n";
        }
        output += "Payment Details:\n";
        for(PaymentDetail paymentDetail : getPaymentDetails())
        {
            output += "\t" + "Name: " + paymentDetail.getName() +
                      " Cost: " +Format.formatMoney(  paymentDetail.getCost()) +
                      " Payment: " + Format.formatMoney( paymentDetail.getPayment() ) +
                      " Change: " + Format.formatMoney( paymentDetail.getChange() ) + "\n";
        }
        output += "\n\n";
        return output;
    }

    public BigDecimal getTotal()
    {
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : getSaleItems())
        {
            total = total.add( saleItem.getExtendedPrice() );

            BigDecimal tax = saleItem.getExtendedPrice().multiply( saleItem.getTaxRate() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
            total = total.add( tax );
        }
        return total;
    }

    public int getSaleItemCount()
    {
        int count = 0;
        for (SaleItem saleItem : getSaleItems())
        {
            count += saleItem.getQuantity();
        }
        return count;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public String getMemberName()
    {
        return Database.getMemberName(getMemberId());
    }

    public List<PaymentDetail> getPaymentDetails()
    {
        return paymentDetails;
    }

    public String getId()
    {
        return id;
    }

    public List<SaleItem> getSaleItems()
    {
        return saleItems;
    }
}