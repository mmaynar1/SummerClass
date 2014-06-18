import java.math.BigDecimal;
import java.util.List;

public final class Sale
{
    private final Member member;
    private final List<SaleItem> saleItems;
    private final List<PaymentDetail> paymentDetails;
    private final String id;

    public Sale( Member member, List<SaleItem> saleItems, List<PaymentDetail> paymentDetails )
    {
        //todo should Sale accept List<PaymentMethods> instead of PaymentDetails and figure it out?
        //todo splitting payments unevenly would be a problem
        this.member = member;
        this.saleItems = saleItems;
        this.paymentDetails = paymentDetails;
        this.id = RandomGenerator.getGuid();
    }

    public Sale( Sale sale )
    {
        this( sale.getMember(), sale.getSaleItems(), sale.getPaymentDetails() );
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
                      " Tax: " + Format.formatMoney(  saleItem.getTax()) + "\n";
        }
        for(PaymentDetail paymentDetail : getPaymentDetails())
        {
            output += paymentDetail.getName() + " " + Format.formatMoney(  paymentDetail.getAmount()) + "\n";
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

            BigDecimal tax = saleItem.getExtendedPrice().multiply( saleItem.getTaxRate() );
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
        return getMember().getId();
    }

    public String getMemberName()
    {
        return getMember().getName();
    }


    public Member getMember()
    {
        return member;
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