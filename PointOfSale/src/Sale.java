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

    public Sale(Sale sale)
    {
        this(sale.getMember(), sale.getSaleItems(), sale.getPaymentDetails());
    }

    public String getMemberName()
    {
        return getMember().getName();
    }

    public String getMemberId()
    {
        return getMember().getId();
    }

    public BigDecimal getTotal()
    {
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : getSaleItems())
        {
            total = total.add( saleItem.getUnitPrice() );
        }
        return total;
    }

    public int getSaleItemCount()
    {
        return getSaleItems().size();
    }

    public Member getMember()
    {
        return member;
    }

    public List<SaleItem> getSaleItems()
    {
        return saleItems;
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