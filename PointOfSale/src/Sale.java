import java.math.BigDecimal;
import java.util.List;

public final class Sale
{

    private final Member member;
   // private final String memberId;
    private final List<InventoryItem> inventoryItems;
    private final List<PaymentDetail> paymentDetails;
    private final String id;

    public Sale( /*String memberId*/ Member member, List<InventoryItem> inventoryItems, List<PaymentDetail> paymentDetails )
    {
        //todo should Sale accept List<PaymentMethods> instead of PaymentDetails and figure it out?
        //todo splitting payments unevenly would be a problem
        this.member = member;
        //this.memberId = memberId;
        this.inventoryItems = inventoryItems;
        this.paymentDetails = paymentDetails;
        this.id = RandomGenerator.getGuid();
    }

    public Sale( Sale sale )
    {
        this( /*sale.getMemberId()*/sale.getMember(), sale.getInventoryItems(), sale.getPaymentDetails() );
    }

    @Override
    public String toString()
    {
        String output = "Name: " + getMemberName() + ", \n" + "Sale Items: ";
        for (InventoryItem item : getInventoryItems())
        {
            output += item.getName() + " " + Format.formatMoney( item.getUnitPrice() ) + " " + item.getId() + ", ";
        }
        output += "\nPayment Details: ";
        for (PaymentDetail detail : getPaymentDetails())
        {
            output += detail.getName() + " " + Format.formatMoney( detail.getAmount() ) + ", ";
        }
        output += "\n\n";
        return output;
    }

    public BigDecimal getTotal()
    {
        BigDecimal total = BigDecimal.ZERO;
        for (InventoryItem inventoryItem : getInventoryItems())
        {
            total = total.add( inventoryItem.getUnitPrice() );
        }
        return total;
    }

    public int getSaleItemCount()
    {
        return getInventoryItems().size();
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

    public List<InventoryItem> getInventoryItems()
    {
        return inventoryItems;
    }

    public List<PaymentDetail> getPaymentDetails()
    {
        return paymentDetails;
    }

    public String getId()
    {
        return id;
    }
}