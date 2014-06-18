import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database
{
    private static final Map<String, Member> members = new HashMap<String, Member>();
    private static final Map<String, InventoryItem> inventoryItems = new HashMap<String, InventoryItem>();
    private static final Map<String, PaymentMethod> paymentMethods = new HashMap<String, PaymentMethod>();

    private Database()
    {
    }

    static
    {
        initializeMembers();
        initializeInventoryItems();
        initializePaymentMethods();
    }

    public static BigDecimal getUnitPrice( String inventoryItemId )
    {
        return getInventoryItems().get( inventoryItemId ).getUnitPrice();
    }

    public static String getInventoryItemName( String inventoryItemId )
    {
        return getInventoryItems().get( inventoryItemId ).getName();
    }

    public static BigDecimal getInventoryItemTaxRate( String inventoryItemId )
    {
        return getInventoryItems().get( inventoryItemId ).getTax().getRate();
    }


    public static List<Sale> getRandomSales( int size )
    {
        List<Member> randomMembers = getRandomMembers();

        List<Sale> sales = new ArrayList<Sale>();
        for (int i = 0; i < size; i++)
        {
            List<InventoryItem> inventoryItems = getRandomInventoryItems();
            List<SaleItem> saleItems = getSaleItems( inventoryItems );
            sales.add( new Sale( randomMembers.get( i ), saleItems, getPaymentDetails( getRandomPaymentMethods(), saleItems ) ) );
        }
        return sales;
    }

    private static List<SaleItem> getSaleItems( List<InventoryItem> inventoryItems )
    {
        List<SaleItem> saleItems = new ArrayList<SaleItem>();
        for (InventoryItem item : inventoryItems)
        {
            int quantity = RandomGenerator.getInt( 1, 6 );
            saleItems.add( new SaleItem( item.getId(), quantity ) );
        }
        return saleItems;
    }

    private static List<PaymentDetail> getPaymentDetails( List<PaymentMethod> paymentMethods, List<SaleItem> saleItems )
    {
        //Divides payments equally over the different payment methods
        int paymentMethodsCount = paymentMethods.size();

        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : saleItems)
        {
            total = total.add( saleItem.getExtendedPrice() );
            total = total.add( saleItem.getTax() );
        }

        List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
        if ( paymentMethodsCount > 0 )
        {
            final int CALCULATION_DECIMAL_PLACES = 30;
            BigDecimal amountPerPaymentMethod = total.divide( new BigDecimal( paymentMethodsCount ), 2, BigDecimal.ROUND_HALF_UP );


            //todo calculate to the penny
            BigDecimal extraMoney = BigDecimal.ZERO;
            BigDecimal roundedTotal = amountPerPaymentMethod.multiply( new BigDecimal( paymentMethodsCount ), MathContext.UNLIMITED );

            if ( total.compareTo( roundedTotal ) != 0 )
            {
                extraMoney = total.subtract( roundedTotal );
            }

            boolean isFirst = true;
            for (PaymentMethod method : paymentMethods)
            {
                if(isFirst)
                {
                    paymentDetails.add( new PaymentDetail( method, amountPerPaymentMethod.add( extraMoney )));
                    isFirst = false;
                }
                else
                {
                    paymentDetails.add( new PaymentDetail( method, amountPerPaymentMethod ) );
                }

            }
        }
        else
        {
            throw new RuntimeException( "There must be at least one payment method" );
        }

        return paymentDetails;
    }

    private static List<Member> getRandomMembers()
    {
        List<Member> randomMembers = new ArrayList<Member>();
        for (int i = 0; i < getListOfMembers().size(); i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, getListOfMembers().size() );
            randomMembers.add( getListOfMembers().get( randomIndex ) );
        }

        return randomMembers;
    }


    private static List<PaymentMethod> getRandomPaymentMethods()
    {

        List<PaymentMethod> randomPaymentMethods = new ArrayList<PaymentMethod>();
        List<PaymentMethod> usedPaymentMethods = new ArrayList<PaymentMethod>();
        int randomNumberOfPaymentMethods = RandomGenerator.getInt( 1, 4 );

        while ( usedPaymentMethods.size() < randomNumberOfPaymentMethods )
        {
            int randomIndex = RandomGenerator.getInt( 0, getListOfPaymentMethods().size() );
            if ( !usedPaymentMethods.contains( getListOfPaymentMethods().get( randomIndex ) ) )
            {
                randomPaymentMethods.add( getListOfPaymentMethods().get( randomIndex ) );
                usedPaymentMethods.add( getListOfPaymentMethods().get( randomIndex ) );
            }
        }

        return randomPaymentMethods;
    }


    private static List<InventoryItem> getRandomInventoryItems()
    {
        List<InventoryItem> randomInventoryItems = new ArrayList<InventoryItem>();

        int randomNumberOfSaleItems = RandomGenerator.getInt( 1, 5 );
        for (int i = 0; i < randomNumberOfSaleItems; i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, getInventoryItems().size() );
            randomInventoryItems.add( getListOfInventoryItems().get( randomIndex ) );
        }

        return randomInventoryItems;
    }

    private static void initializePaymentMethods()
    {
        getPaymentMethods().put( PaymentMethod.CASH.getAbcCode(), PaymentMethod.CASH );
        getPaymentMethods().put( PaymentMethod.VISA.getAbcCode(), PaymentMethod.VISA );
        getPaymentMethods().put( PaymentMethod.MC.getAbcCode(), PaymentMethod.MC );
        getPaymentMethods().put( PaymentMethod.COUPON.getAbcCode(), PaymentMethod.COUPON );
    }

    private static void initializeInventoryItems()
    {
        List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
        inventoryItems.add( new InventoryItem( "Water", 10, Tax.None ) );
        inventoryItems.add( new InventoryItem( "Smoothie", 10, Tax.None ) );
        inventoryItems.add( new InventoryItem( "Muscle Milk", 10, Tax.None ) );
        inventoryItems.add( new InventoryItem( "Personal Training", 10, Tax.Tax ) );
        inventoryItems.add( new InventoryItem( "Tanning", 10, Tax.Tax ) );
        inventoryItems.add( new InventoryItem( "T-Shirt", 10, Tax.Tax ) );
        inventoryItems.add( new InventoryItem( "Towel", 10, Tax.Tax ) );
        inventoryItems.add( new InventoryItem( "Protein Bar", 10, Tax.None ) );
        inventoryItems.add( new InventoryItem( "Vitamins", 10, Tax.None ) );
        inventoryItems.add( new InventoryItem( "Gloves", 10, Tax.Tax ) );

        for (InventoryItem item : inventoryItems)
        {
            getInventoryItems().put( item.getId(), item );
        }
    }

    private static void initializeMembers()
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

        for (Member member : members)
        {
            getMembers().put( member.getId(), member );
        }
    }

    private static List<Member> getListOfMembers()
    {
        return new ArrayList<Member>( getMembers().values() );
    }

    private static List<PaymentMethod> getListOfPaymentMethods()
    {
        return new ArrayList<PaymentMethod>( getPaymentMethods().values() );
    }

    private static List<InventoryItem> getListOfInventoryItems()
    {
        return new ArrayList<InventoryItem>( getInventoryItems().values() );
    }

    private static Map<String, Member> getMembers()
    {
        return members;
    }

    private static Map<String, InventoryItem> getInventoryItems()
    {
        return inventoryItems;
    }

    private static Map<String, PaymentMethod> getPaymentMethods()
    {
        return paymentMethods;
    }
}
