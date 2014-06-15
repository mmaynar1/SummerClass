import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PointOfSaleSystem
{

    public List<Sale> getRandomSales( int size )
    {
        List<Member> members = getRandomMembers();
        List<Sale> sales = new ArrayList<Sale>();
        for (int i = 0; i < size; i++)
        {
            List<SaleItem> saleItems = getRandomSaleItems();
            sales.add( new Sale( members.get( i ), saleItems, getPaymentDetails( getRandomPaymentMethods(), saleItems ) ) );
        }
        return sales;
    }

    private List<PaymentDetail> getPaymentDetails( List<PaymentMethod> paymentMethods, List<SaleItem> saleItems )
    {
        //Divides payments equally over the different payment methods
        int paymentMethodsCount = paymentMethods.size();

        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem item : saleItems)
        {
            total = total.add( item.getUnitPrice() );
        }

        List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
        if ( paymentMethodsCount > 0 )
        {
            final int PRECISE_DECIMAL_PLACES = 30;
            BigDecimal amountPerPaymentMethod = total.divide( new BigDecimal( paymentMethodsCount ), PRECISE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
            for (int i = 0; i < paymentMethodsCount; i++)
            {
                paymentDetails.add( new PaymentDetail( paymentMethods.get( i ), amountPerPaymentMethod ) );
            }
        }
        else
        {
            throw new RuntimeException( "There must be at least one payment method" );
        }

        return paymentDetails;
    }


    private List<PaymentMethod> getRandomPaymentMethods()
    {
        List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
        paymentMethods.add( PaymentMethod.CASH );
        paymentMethods.add( PaymentMethod.VISA );
        paymentMethods.add( PaymentMethod.MC );
        paymentMethods.add( PaymentMethod.COUPON );

        List<PaymentMethod> randomPaymentMethods = new ArrayList<PaymentMethod>();

        int randomNumberOfPaymentMethods = RandomGenerator.getInt( 1, 4 );
        for (int i = 0; i < randomNumberOfPaymentMethods; i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, paymentMethods.size() );
            randomPaymentMethods.add( paymentMethods.get( randomIndex ) );
        }

        return randomPaymentMethods;
    }


    private List<SaleItem> getRandomSaleItems()
    {
        List<SaleItem> saleItems = new ArrayList<SaleItem>();
        saleItems.add( new SaleItem( "Water", 10 ) );
        saleItems.add( new SaleItem( "Smoothie", 10 ) );
        saleItems.add( new SaleItem( "Muscle Milk", 10 ) );
        saleItems.add( new SaleItem( "Personal Training", 10 ) );
        saleItems.add( new SaleItem( "Tanning", 10 ) );
        saleItems.add( new SaleItem( "T-Shirt", 10 ) );
        saleItems.add( new SaleItem( "Towel", 10 ) );
        saleItems.add( new SaleItem( "Protein Bar", 10 ) );
        saleItems.add( new SaleItem( "Vitamins", 10 ) );
        saleItems.add( new SaleItem( "Gloves", 10 ) );

        List<SaleItem> randomSaleItems = new ArrayList<SaleItem>();

        int randomNumberOfSaleItems = RandomGenerator.getInt( 1, 5 );
        for (int i = 0; i < randomNumberOfSaleItems; i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, saleItems.size() );
            randomSaleItems.add( saleItems.get( randomIndex ) );
        }

        return randomSaleItems;
    }

    private List<Member> getRandomMembers()
    {
        List<Member> members = new ArrayList<Member>();
        members.add( new Member( "Mitch Maynard" ) );
        members.add( new Member( "Bill Cosby" ) );
        members.add( new Member( "Katniss Everdeen" ) );
        members.add( new Member( "Walter White" ) );
        members.add( new Member( "Babe Ruth" ) );
        members.add( new Member( "Reggie Miller" ) );
        members.add( new Member( "Larry Bird" ) );
        members.add( new Member( "Tiger Woods" ) );
        members.add( new Member( "Taylor Swift" ) );
        members.add( new Member( "George Bush" ) );

        List<Member> randomMembers = new ArrayList<Member>();
        for (int i = 0; i < members.size(); i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, members.size() );
            randomMembers.add( members.get( randomIndex ) );
        }

        return randomMembers;
    }
}