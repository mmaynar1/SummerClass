import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PointOfSaleSystem
{
    public void simulateRandomSales( int numberOfSales )
    {
        //Database database = new Database();
        Database database = Database.getInstance();
        List<Sale> sales = database.getRandomSales( numberOfSales );
        System.out.println( "---------- SALES MADE ----------" );
        for (Sale sale : sales)
        {
            System.out.println( sale );
        }

        Reports reports = new Reports();
        reports.generateMemberReport( sales );
        reports.generateSalesItemReport( sales );
        reports.generatePaymentMethodReport( sales );

        System.out.println();
        System.out.println("******************************");

        Database database2 = Database.getInstance();
        List<Sale> sales2 = database.getRandomSales( numberOfSales );
        System.out.println( "---------- SALES MADE ----------" );
        for (Sale sale : sales2)
        {
            System.out.println( sale );
        }

        Reports reports2 = new Reports();
        reports2.generateMemberReport( sales2 );
        reports2.generateSalesItemReport( sales2 );
        reports2.generatePaymentMethodReport( sales2 );
    }

    /*private List<Sale> getRandomSales( int size )
    {
        List<Member> members = getRandomMembers();
        List<InventoryItem> inventoryItems = createSaleItems();
        List<PaymentMethod> paymentMethods = createPaymentMethods();

        List<Sale> sales = new ArrayList<Sale>();
        for (int i = 0; i < size; i++)
        {
            List<InventoryItem> items = getRandomSaleItems( inventoryItems );
            sales.add( new Sale( members.get( i ), items, getPaymentDetails( getRandomPaymentMethods( paymentMethods ), items ) ) );
        }
        return sales;
    }*/

    /*private List<PaymentDetail> getPaymentDetails( List<PaymentMethod> paymentMethods, List<InventoryItem> inventoryItems )
    {
        //Divides payments equally over the different payment methods
        int paymentMethodsCount = paymentMethods.size();

        BigDecimal total = BigDecimal.ZERO;
        for (InventoryItem item : inventoryItems)
        {
            total = total.add( item.getUnitPrice() );
        }

        List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
        if ( paymentMethodsCount > 0 )
        {
            final int CALCULATION_DECIMAL_PLACES = 30;
            BigDecimal amountPerPaymentMethod = total.divide( new BigDecimal( paymentMethodsCount ), CALCULATION_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );


            //todo calculate to the penny
*//*            BigDecimal roundedTotal = amountPerPaymentMethod.multiply(  new BigDecimal(  paymentMethodsCount));
            if(roundedTotal.compareTo( total ) > 0)
            {
                BigDecimal extraMoney = total.subtract( roundedTotal );

            }*//*

            for (PaymentMethod method : paymentMethods)
            {
                paymentDetails.add( new PaymentDetail( method, amountPerPaymentMethod ) );
            }
        }
        else
        {
            throw new RuntimeException( "There must be at least one payment method" );
        }

        return paymentDetails;
    }
*/

   /* private List<PaymentMethod> getRandomPaymentMethods( List<PaymentMethod> paymentMethods )
    {

        List<PaymentMethod> randomPaymentMethods = new ArrayList<PaymentMethod>();
        List<PaymentMethod> usedPaymentMethods = new ArrayList<PaymentMethod>();
        int randomNumberOfPaymentMethods = RandomGenerator.getInt( 1, 4 );

        while ( usedPaymentMethods.size() < randomNumberOfPaymentMethods )
        {
            int randomIndex = RandomGenerator.getInt( 0, paymentMethods.size() );
            if ( !usedPaymentMethods.contains( paymentMethods.get( randomIndex ) ) )
            {
                randomPaymentMethods.add( paymentMethods.get( randomIndex ) );
                usedPaymentMethods.add( paymentMethods.get( randomIndex ) );
            }
        }

        return randomPaymentMethods;
    }


    private List<InventoryItem> getRandomSaleItems( List<InventoryItem> inventoryItems )
    {
        List<InventoryItem> randomInventoryItems = new ArrayList<InventoryItem>();

        int randomNumberOfSaleItems = RandomGenerator.getInt( 1, 5 );
        for (int i = 0; i < randomNumberOfSaleItems; i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, inventoryItems.size() );
            randomInventoryItems.add( inventoryItems.get( randomIndex ) );
        }

        return randomInventoryItems;
    }

    private List<PaymentMethod> createPaymentMethods()
    {
        List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
        paymentMethods.add( PaymentMethod.CASH );
        paymentMethods.add( PaymentMethod.VISA );
        paymentMethods.add( PaymentMethod.MC );
        paymentMethods.add( PaymentMethod.COUPON );
        return paymentMethods;
    }

    private List<InventoryItem> createSaleItems()
    {
        List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
        inventoryItems.add( new InventoryItem( "Water", 10 ) );
        inventoryItems.add( new InventoryItem( "Smoothie", 10 ) );
        inventoryItems.add( new InventoryItem( "Muscle Milk", 10 ) );
        inventoryItems.add( new InventoryItem( "Personal Training", 10 ) );
        inventoryItems.add( new InventoryItem( "Tanning", 10 ) );
        inventoryItems.add( new InventoryItem( "T-Shirt", 10 ) );
        inventoryItems.add( new InventoryItem( "Towel", 10 ) );
        inventoryItems.add( new InventoryItem( "Protein Bar", 10 ) );
        inventoryItems.add( new InventoryItem( "Vitamins", 10 ) );
        inventoryItems.add( new InventoryItem( "Gloves", 10 ) );

        return inventoryItems;
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
    }*/
}