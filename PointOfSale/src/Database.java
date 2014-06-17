import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database
{
    private final Map<String, Member> members = new HashMap<String, Member>();
    private final Map<String, InventoryItem> inventoryItems = new HashMap<String, InventoryItem>();
    private final Map<String, PaymentMethod> paymentMethods = new HashMap<String, PaymentMethod>();

    private static Database database = new Database();

    private Database()
    {
        initializeMembers();
        initializeInventoryItems();
        initializePaymentMethods();
    }

    public static Database getInstance()
    {
        return database;
    }


    public List<Sale> getRandomSales( int size )
    {
        List<Member> randomMembers = getRandomMembers();

        List<Sale> sales = new ArrayList<Sale>();
        for (int i = 0; i < size; i++)
        {
            List<InventoryItem> items = getRandomInventoryItems();
            sales.add( new Sale( randomMembers.get( i ), items, getPaymentDetails( getRandomPaymentMethods(), items ) ) );
        }
        return sales;
    }

    private List<PaymentDetail> getPaymentDetails( List<PaymentMethod> paymentMethods, List<InventoryItem> inventoryItems )
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
    /*            BigDecimal roundedTotal = amountPerPaymentMethod.multiply(  new BigDecimal(  paymentMethodsCount));
                if(roundedTotal.compareTo( total ) > 0)
                {
                    BigDecimal extraMoney = total.subtract( roundedTotal );

                }*/

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

    private List<Member> getRandomMembers()
    {
        List<Member> randomMembers = new ArrayList<Member>();
        for (int i = 0; i < getListOfMembers().size(); i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, getListOfMembers().size() );
            randomMembers.add( getListOfMembers().get( randomIndex ) );
        }

        return randomMembers;
    }


    private List<PaymentMethod> getRandomPaymentMethods()
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


    private List<InventoryItem> getRandomInventoryItems()
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

    private void initializePaymentMethods()
    {
        getPaymentMethods().put( PaymentMethod.CASH.getAbcCode(), PaymentMethod.CASH );
        getPaymentMethods().put( PaymentMethod.VISA.getAbcCode(), PaymentMethod.VISA );
        getPaymentMethods().put( PaymentMethod.MC.getAbcCode(), PaymentMethod.MC );
        getPaymentMethods().put( PaymentMethod.COUPON.getAbcCode(), PaymentMethod.COUPON );
    }

    private void initializeInventoryItems()
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

        for (InventoryItem item : inventoryItems)
        {
            getInventoryItems().put( item.getId(), item );
        }
    }

    private void initializeMembers()
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

    private List<Member> getListOfMembers()
    {
        return new ArrayList<Member>( getMembers().values() );
    }

    private List<PaymentMethod> getListOfPaymentMethods()
    {
        return new ArrayList<PaymentMethod>( getPaymentMethods().values() );
    }

    private List<InventoryItem> getListOfInventoryItems()
    {
        return new ArrayList<InventoryItem>( getInventoryItems().values() );
    }

    private Map<String, Member> getMembers()
    {
        return members;
    }

    private Map<String, InventoryItem> getInventoryItems()
    {
        return inventoryItems;
    }

    private Map<String, PaymentMethod> getPaymentMethods()
    {
        return paymentMethods;
    }
}
