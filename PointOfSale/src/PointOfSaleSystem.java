import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class PointOfSaleSystem
{

    public static final int MONEY_DECIMAL_PLACES = 2;
    public static final int NUMBER_OF_SALES = 100;

    private final Drawer drawer;

    public PointOfSaleSystem( )
    {
        this.drawer = new Drawer();
    }

    public void simulateRandomSales()
    {
        makeRandomSales( NUMBER_OF_SALES );
        List<Sale> listOfSales = getSalesFromDatabase();
        System.out.println( "---------- SALES MADE ----------" );
        for (Sale sale : listOfSales)
        {
            System.out.println( sale );
        }

        Reports reports = new Reports();
        reports.generateDrawerSummary( getDrawer() );
        reports.generateMemberReport( listOfSales );
        reports.generateSalesItemReport( listOfSales );
        reports.generatePaymentMethodReport( listOfSales );

    }

    private List<Sale> getSalesFromDatabase()
    {
        return new ArrayList<Sale>( Database.getSales().values() );
    }

    private void makeRandomSales( int size )
    {
        List<Member> randomMembers = getRandomMembers( size );

        for (int i = 0; i < size; i++)
        {
            Map<String, SaleItem> saleItems = Database.getRandomSaleItems();
            Sale sale = new Sale( randomMembers.get( i ).getId(),
                                  new ArrayList<SaleItem>( saleItems.values() ),
                                  getPaymentDetails( getRandomPaymentMethods(), new ArrayList<SaleItem>( saleItems.values() ) ) );
            getDrawer().updateBalance( sale );
            Database.getSales().put( sale.getId(), sale );
        }
    }

    private List<Member> getRandomMembers( int size )
    {
        List<Member> randomMembers = new ArrayList<Member>();
        for (int i = 0; i < size; i++)
        {
            int randomIndex = RandomGenerator.getInt( 0, Database.getListOfMembers().size() );
            randomMembers.add( Database.getListOfMembers().get( randomIndex ) );
        }

        return randomMembers;
    }

    private BigDecimal getGrandTotal( List<SaleItem> saleItems )
    {
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : saleItems)
        {
            total = total.add( saleItem.getExtendedPrice() );
            total = total.add( saleItem.getTax() );
        }
        return total;
    }

    private List<PaymentDetail> getPaymentDetails( List<PaymentMethod> paymentMethods, List<SaleItem> saleItems )
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

    private List<PaymentMethod> getRandomPaymentMethods()
    {
        List<PaymentMethod> randomPaymentMethods = new ArrayList<PaymentMethod>();
        List<PaymentMethod> usedPaymentMethods = new ArrayList<PaymentMethod>();


        PaymentMethod randomPaymentMethod = Database.getRandomPaymentMethod();
        randomPaymentMethods.add( randomPaymentMethod );
        usedPaymentMethods.add( randomPaymentMethod );

        int randomNumberOfPaymentMethods = getRandomNumberOfPaymentMethods();
        while ( usedPaymentMethods.size() < randomNumberOfPaymentMethods )
        {
            randomPaymentMethod = Database.getRandomPaymentMethod();
            if ( !(randomPaymentMethod == PaymentMethod.CASH && usedPaymentMethods.contains( PaymentMethod.CASH )) )
            {
                randomPaymentMethods.add( randomPaymentMethod );
                usedPaymentMethods.add( randomPaymentMethod );
            }
        }

        return randomPaymentMethods;
    }

    private int getRandomNumberOfPaymentMethods()
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
        return randomNumberOfPaymentMethods;
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
        BigDecimal cashPayment = cost.setScale( 2, RoundingMode.HALF_UP );
        BigDecimal dollar = new BigDecimal( 1 );
        if ( !(cashPayment.remainder( BigDecimal.TEN ).equals( BigDecimal.ZERO )) )
        {
            cashPayment = cashPayment.add( BigDecimal.TEN );

            boolean isFirst = true;
            while ( !(cashPayment.remainder( BigDecimal.TEN ).equals( BigDecimal.ZERO )) )
            {
                if ( isFirst )
                {
                    cashPayment = cashPayment.add( new BigDecimal( .99 ) );
                    cashPayment = new BigDecimal( cashPayment.intValue() );
                    isFirst = false;
                }

                cashPayment = cashPayment.subtract( dollar );
            }
        }

        return cashPayment;
    }

    public Drawer getDrawer()
    {
        return drawer;
    }
}