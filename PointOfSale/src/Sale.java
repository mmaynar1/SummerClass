import utility.Format;
import utility.RandomGenerator;

import java.math.BigDecimal;
import java.util.List;

public final class Sale
{
    public static final String DELIMITER = ":";

    private final String clubId;
    private final String memberId;
    private final List<SaleItem> saleItems;
    private final List<PaymentDetail> paymentDetails;
    private final String id;
    private final BigDecimal discountRate;

    public Sale( String clubId, String memberId, List<SaleItem> saleItems, List<PaymentDetail> paymentDetails, BigDecimal discountRate )
    {
        this( clubId, memberId, saleItems, paymentDetails, RandomGenerator.getGuid(), discountRate );
    }

    public Sale( String clubId, String memberId, List<SaleItem> saleItems, List<PaymentDetail> paymentDetails, String saleId, BigDecimal discountRate )
    {
        this.clubId = clubId;
        this.memberId = memberId;
        this.saleItems = saleItems;
        this.paymentDetails = paymentDetails;
        this.id = saleId;
        this.discountRate = discountRate;
    }

    public Sale( Sale sale, BigDecimal discountRate )
    {
        this( sale.getClubId(), sale.getMemberId(), sale.getSaleItems(), sale.getPaymentDetails(), discountRate );
    }

    @Override
    public String toString()
    {
        String output = "Member: \n\t" + getMemberName() + " " + getMemberId() + "\n";
        output += "Discount Rate: " + Format.formatPercentage( getDiscountRate() );
        output += "\n";
        output += "Items:\n";
        for (SaleItem saleItem : getSaleItems())
        {
            output += "\tName: " + saleItem.getInventoryItemName() +
                      " Id: " + saleItem.getInventoryItemId() +
                      " Ext Price: " + Format.formatMoney( saleItem.getExtendedPrice() ) +
                      " Discount: " + Format.formatMoney( saleItem.getDiscount() ) +
                      " Sub Total: " + Format.formatMoney( saleItem.getSubTotal() ) +
                      " Quantity: " + saleItem.getQuantity() +
                      " Tax Rate: " + Format.formatPercentage( saleItem.getTaxRate() ) +
                      " Tax: " + Format.formatMoney( saleItem.getTax() ) + "\n";
        }
        output += "Payment Details:\n";
        for (PaymentDetail paymentDetail : getPaymentDetails())
        {
            output += "\t" + "Name: " + paymentDetail.getName() +
                      " Cost: " + Format.formatMoney( paymentDetail.getCost() ) +
                      " Payment: " + Format.formatMoney( paymentDetail.getPayment() ) +
                      " Change: " + Format.formatMoney( paymentDetail.getChange() ) + "\n";
        }
        output += "\n\n";
        return output;
    }

    public BigDecimal getDiscount()
    {
        BigDecimal discount = BigDecimal.ZERO;
        for (SaleItem saleItem : getSaleItems())
        {
            discount = discount.add( saleItem.getDiscount() );
        }
        return discount;
    }

    public BigDecimal getSubTotal()
    {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (SaleItem saleItem : getSaleItems())
        {
            subTotal = subTotal.add( saleItem.getSubTotal() );
        }
        return subTotal;
    }

    private BigDecimal getExtendedPrice()
    {
        BigDecimal extendedPrice = BigDecimal.ZERO;
        for (SaleItem saleItem : getSaleItems())
        {
            extendedPrice = extendedPrice.add( saleItem.getExtendedPrice() );
        }
        return extendedPrice;
    }

    public BigDecimal getTotal()
    {
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : getSaleItems())
        {
            total = total.add( saleItem.getTotal() );
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

    public String getTextRepresentation()
    {
        String line = DELIMITER + getClubId() + DELIMITER +
                      getMemberId();

        for (SaleItem saleItem : getSaleItems())
        {
            line += saleItem.getTextRepresentation();
        }

        //line += DELIMITER;

        for (PaymentDetail paymentDetail : getPaymentDetails())
        {
            line += paymentDetail.getTextRepresentation();
        }

        line += DELIMITER;

        line += getId() + DELIMITER;
        line += Format.formatRate( getDiscountRate() );

        return line;
    }


    public String getMemberId()
    {
        return memberId;
    }

    public String getMemberName()
    {
        return Database.getMemberName( getMemberId() );
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

    private BigDecimal getDiscountRate()
    {
        return discountRate;
    }

    public String getClubId()
    {
        return clubId;
    }
}