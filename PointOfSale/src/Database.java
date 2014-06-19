import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class Database
{
    public static final int MONEY_DECIMAL_PLACES = 2;
    public static final int NUMBER_OF_SALES = 100;

    private static final Map<String, Member> members = new HashMap<String, Member>();
    private static final Map<String, InventoryItem> inventoryItems = new HashMap<String, InventoryItem>();
    private static final Map<String, PaymentMethod> paymentMethods = new HashMap<String, PaymentMethod>();
    private static final Map<String, Sale> sales = new HashMap<String, Sale>();


    private Database()
    {
    }

    static
    {
        initializeMembers();
        initializeInventoryItems();
        initializePaymentMethods();
        initializeRandomSales();
    }

    public static Map<String, Sale> getRandomSales( int size )
    {
        List<Member> randomMembers = getRandomMembers( size );

        for (int i = 0; i < size; i++)
        {
            Map<String, SaleItem> saleItems = Database.getRandomSaleItems();

            Sale sale = new Sale( randomMembers.get( i ).getId(), new ArrayList<SaleItem>( saleItems.values() ), getPaymentDetails( getRandomPaymentMethods(), new ArrayList<SaleItem>( saleItems.values() ) ) );
            getSales().put( sale.getId(), sale );
        }
        return sales;
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

    public static String getMemberName( String memberId )
    {
        return getMembers().get( memberId ).getName();
    }

    public static String getPaymentMethodName( String paymentMethodId )
    {
        return getPaymentMethods().get( paymentMethodId ).getName();
    }

    public static String getPaymentMethodAbcCode( String paymentMethodId )
    {
        return getPaymentMethods().get( paymentMethodId ).getAbcCode();
    }

    public static Map<String, SaleItem> getRandomSaleItems()
    {
        Map<String, SaleItem> saleItems = new HashMap<String, SaleItem>();
        List<InventoryItem> inventoryItems = getRandomInventoryItems();
        for (InventoryItem inventoryItem : inventoryItems)
        {
            int quantity = RandomGenerator.getInt( 1, 6 );
            SaleItem saleItem = new SaleItem( inventoryItem.getId(), quantity );
            saleItems.put( saleItem.getId(), saleItem );
        }
        return saleItems;
    }

    public static List<PaymentDetail> getPaymentDetails( List<PaymentMethod> paymentMethods, List<SaleItem> saleItems )
    {
        //Divides payments equally over the different payment methods

        BigDecimal grandTotal = getGrandTotal( saleItems );

        List<PaymentDetail> paymentDetails;
        if ( paymentMethods.size() > 0 )
        {
            BigDecimal amountPerPaymentMethod = grandTotal.divide( new BigDecimal( paymentMethods.size() ), MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );

            BigDecimal extraMoney = BigDecimal.ZERO;
            BigDecimal roundedTotal = amountPerPaymentMethod.multiply( new BigDecimal( paymentMethods.size() ), MathContext.UNLIMITED );

            if ( grandTotal.compareTo( roundedTotal ) != 0 )
            {
                extraMoney = grandTotal.subtract( roundedTotal );
            }

            paymentDetails = generatePaymentDetails( paymentMethods, amountPerPaymentMethod, extraMoney );
        }
        else
        {
            throw new RuntimeException( "There must be at least one payment method" );
        }

        return paymentDetails;
    }

    private static List<PaymentDetail> generatePaymentDetails( List<PaymentMethod> paymentMethods, BigDecimal amountPerPaymentMethod, BigDecimal extraMoney )
    {
        List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();

        boolean isFirst = true;
        final BigDecimal cost = amountPerPaymentMethod;
        final BigDecimal oddCost = cost.add( extraMoney );
        for (PaymentMethod paymentMethod : paymentMethods)
        {
            if ( isFirst )
            {
                if ( paymentMethod == PaymentMethod.CASH )
                {

                    paymentDetails.add( new PaymentDetail( paymentMethod.getAbcCode(), oddCost, getCashPayment( oddCost ) ) );
                    isFirst = false;
                }
                else
                {
                    paymentDetails.add( new PaymentDetail( paymentMethod.getAbcCode(), oddCost, oddCost ) );
                    isFirst = false;
                }
            }
            else
            {
                if ( paymentMethod == PaymentMethod.CASH )
                {
                    paymentDetails.add( new PaymentDetail( paymentMethod.getAbcCode(), cost, getCashPayment( cost ) ) );
                }
                else
                {
                    paymentDetails.add( new PaymentDetail( paymentMethod.getAbcCode(), cost, cost ) );
                }

            }

        }
        return paymentDetails;
    }

    private static BigDecimal getCashPayment( BigDecimal cost )
    {
        BigDecimal cashPayment = cost.setScale( 2 , RoundingMode.HALF_UP);
        BigDecimal dollar = new BigDecimal( 1 );
        if ( !(cashPayment.remainder( BigDecimal.TEN ).equals( BigDecimal.ZERO)) )
        {
            cashPayment = cashPayment.add( BigDecimal.TEN );

            boolean isFirst = true;
            while ( !(cashPayment.remainder( BigDecimal.TEN ).equals( BigDecimal.ZERO )) )
            {
                if(isFirst)
                {
                    cashPayment = cashPayment.add( new BigDecimal( .99 ) );
                    cashPayment = new BigDecimal( cashPayment.intValue());
                    isFirst = false;
                }

                cashPayment = cashPayment.subtract( dollar );
            }
        }

        return cashPayment;
    }

    private static BigDecimal getGrandTotal( List<SaleItem> saleItems )
    {
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : saleItems)
        {
            total = total.add( saleItem.getExtendedPrice() );
            total = total.add( saleItem.getTax() );
        }
        return total;
    }

    private static List<Member> getRandomMembers( int size )
    {
        List<Member> randomMembers = new ArrayList<Member>();
        for (int i = 0; i < size; i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, getListOfMembers().size() );
            randomMembers.add( getListOfMembers().get( randomIndex ) );
        }

        return randomMembers;
    }

    private static List<PaymentMethod> getRandomPaymentMethods()
    {

        int percentage = RandomGenerator.getInt( 1, 100 );
        int randomNumberOfPaymentMethods;
        if ( percentage <= 80 )
        {
            randomNumberOfPaymentMethods = 1;
        }
        else if ( percentage > 80 && percentage < 95 )
        {
            randomNumberOfPaymentMethods = 2;
        }
        else
        {
            randomNumberOfPaymentMethods = 3;
        }

        List<PaymentMethod> randomPaymentMethods = new ArrayList<PaymentMethod>();
        List<PaymentMethod> usedPaymentMethods = new ArrayList<PaymentMethod>();

        int randomIndex = RandomGenerator.getInt( 0, getPaymentMethods().size() );
        randomPaymentMethods.add( getListOfPaymentMethods().get( randomIndex ) );
        usedPaymentMethods.add( getListOfPaymentMethods().get( randomIndex ) );

        while ( usedPaymentMethods.size() < randomNumberOfPaymentMethods )
        {
            randomIndex = RandomGenerator.getInt( 0, getPaymentMethods().size() );
            if ( !(getListOfPaymentMethods().get( randomIndex ) == PaymentMethod.CASH && usedPaymentMethods.contains( PaymentMethod.CASH )) )
            {
                randomPaymentMethods.add( getListOfPaymentMethods().get( randomIndex ) );
                usedPaymentMethods.add( getListOfPaymentMethods().get( randomIndex ) );
            }
        }

        return randomPaymentMethods;
    }


    public static List<InventoryItem> getRandomInventoryItems()
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

    private static void initializeRandomSales()
    {
        getRandomSales( NUMBER_OF_SALES );
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

    public static Map<String, Sale> getSales()
    {
        return sales;
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

    public static PaymentMethod getPaymentMethod( String paymentMethodId )
    {
        return getPaymentMethods().get( paymentMethodId );
    }
}