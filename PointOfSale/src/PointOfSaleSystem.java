import java.math.BigDecimal;
import java.util.*;

public class PointOfSaleSystem
{
    public static final int MONEY_DECIMAL_PLACES = 2;
    public static final int NUMBER_OF_SALES = 100;

    private final Map<String, List<Drawer>> drawers;

    public PointOfSaleSystem()
    {
        this.drawers = new HashMap<String, List<Drawer>>();
        for (Club club : Database.getListOfClubs())
        {
            List<Drawer> drawers = new ArrayList<Drawer>();
            for (PaymentMethod paymentMethod : PaymentMethod.values())
            {
                drawers.add( new Drawer( paymentMethod.getAbcCode(), paymentMethod.getStartingBalance() ) );
            }

            getDrawers().put( club.getId(), drawers );
        }
    }

    public void simulateRandomSales()
    {
        makeRandomSales( NUMBER_OF_SALES );
        List<Sale> listOfSales = getSalesFromDatabase();
        System.out.println( "---------- SALES MADE ----------" );
        for (Sale sale : listOfSales)
        {
            System.out.println( sale );
            System.out.println( sale.getTextRepresentation() );
            System.out.println();

        }


        Reports reports = new Reports();

        reports.createSalesFile( listOfSales );

        reports.createSales();
        reports.generateDrawerSummary( getDrawers() );
        reports.generateMemberReport( listOfSales );
        reports.generateSalesItemReport( listOfSales );
        reports.generatePaymentMethodReport( listOfSales );
    }

    private BigDecimal getRandomDiscountRate()
    {
        //todo make this random
        return new BigDecimal( .10 );
    }


    private List<Sale> getSalesFromDatabase()
    {
        return new ArrayList<Sale>( Database.getSales().values() );
    }

    private void makeRandomSales( int size )
    {
        List<Member> randomMembers = getRandomMembers( size );

        for (int i = 0; i < size; ++i)
        {
            BigDecimal randomDiscountRate = getRandomDiscountRate();
            List<SaleItem> saleItems = new ArrayList<SaleItem>( Database.getRandomSaleItems( randomDiscountRate ).values() );

            Sale sale = new Sale( Database.getRandomClubId( "ABC" ),
                                  randomMembers.get( i ).getId(),
                                  saleItems,
                                  getPaymentDetails( getRandomPaymentMethods(), saleItems ), randomDiscountRate );

            updateDrawers( sale );

            Database.getSales().put( sale.getId(), sale );
        }
    }

    private void updateDrawers( Sale sale )
    {
        for (Drawer drawer : getDrawers().get( sale.getClubId() ))
        {
            updateDrawer( sale, drawer );
        }
    }

    private void updateDrawer( Sale sale, Drawer drawer )
    {
        for (PaymentDetail paymentDetail : sale.getPaymentDetails())
        {
            if ( drawer.getPaymentMethodAbcCode().equals( paymentDetail.getAbcCode() ) )
            {
                drawer.update( paymentDetail );
            }
        }
    }

    private List<Member> getRandomMembers( int size )
    {
        List<Member> randomMembers = new ArrayList<Member>();
        for (int i = 0; i < size; ++i)
        {
            int randomIndex = RandomGenerator.getInt( 0, Database.getListOfMembers().size() );
            randomMembers.add( Database.getListOfMembers().get( randomIndex ) );
        }

        return randomMembers;
    }

    private BigDecimal getGrandTotal( List<SaleItem> saleItems )
    {
/*        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : saleItems)
        {
            total = total.add( saleItem.getExtendedPrice() ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
            total = total.add( saleItem.getTax() ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
        }
        return total.setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );*/
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem saleItem : saleItems)
        {
            total = total.add( saleItem.getTotal() );
        }

        return total;
    }

    private List<PaymentDetail> getPaymentDetails( List<PaymentMethod> paymentMethods, List<SaleItem> saleItems )
    {
        BigDecimal grandTotal = getGrandTotal( saleItems ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );

        List<PaymentDetail> paymentDetails;

        if ( paymentMethods.size() > 0 )
        {
            BigDecimal amountPerPaymentMethod = grandTotal.divide( new BigDecimal( paymentMethods.size() ), MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );

            BigDecimal roundedTotal = amountPerPaymentMethod.multiply( new BigDecimal( paymentMethods.size() ) ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );

            BigDecimal extraMoney = grandTotal.subtract( roundedTotal );

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

        PaymentMethod randomPaymentMethod = PaymentMethod.getRandomPaymentMethod();
        randomPaymentMethods.add( randomPaymentMethod );
        usedPaymentMethods.add( randomPaymentMethod );

        int randomNumberOfPaymentMethods = getRandomNumberOfPaymentMethods();
        while ( usedPaymentMethods.size() < randomNumberOfPaymentMethods )
        {
            randomPaymentMethod = PaymentMethod.getRandomPaymentMethod();
            boolean onlyOneInstanceAllowed = randomPaymentMethod.getNumberOfInstancesAllowed() == 1;
            boolean paymentMethodHasBeenUsed = usedPaymentMethods.contains( randomPaymentMethod );

            if ( !(onlyOneInstanceAllowed && paymentMethodHasBeenUsed) )
            {
                randomPaymentMethods.add( randomPaymentMethod );
                usedPaymentMethods.add( randomPaymentMethod );
            }
        }

        return randomPaymentMethods;
    }

    private int getRandomNumberOfPaymentMethods()
    {
        final int mostCommon = 80;
        final int leastCommon = 95;

        int percentage = RandomGenerator.getInt( 1, 101 );
        int randomNumberOfPaymentMethods;
        if ( percentage <= mostCommon )
        {
            randomNumberOfPaymentMethods = 1;
        }
        else if ( percentage > mostCommon && percentage < leastCommon )
        {
            randomNumberOfPaymentMethods = 2;
        }
        else
        {
            randomNumberOfPaymentMethods = 3;
        }
        return randomNumberOfPaymentMethods;
    }

    private List<PaymentDetail> generatePaymentDetails( List<PaymentMethod> paymentMethods, BigDecimal amountPerPaymentMethod, BigDecimal extraMoney )
    {
        List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();

        final BigDecimal cost = amountPerPaymentMethod.setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
        final BigDecimal oddCost = cost.add( extraMoney ).setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );

        boolean isFirst = true;
        for (PaymentMethod paymentMethod : paymentMethods)
        {
            if ( isFirst )
            {
                paymentDetails.add( new PaymentDetail( paymentMethod.getAbcCode(), oddCost, getPayment( paymentMethod, oddCost ) ) );
                isFirst = false;
            }
            else
            {
                paymentDetails.add( new PaymentDetail( paymentMethod.getAbcCode(), cost, getPayment( paymentMethod, cost ) ) );
            }

        }
        return paymentDetails;
    }

    private static BigDecimal getPayment( PaymentMethod paymentMethod, BigDecimal cost )
    {
        BigDecimal payment = cost;
        if ( paymentMethod.isRounded() )
        {
            BigDecimal dollar = new BigDecimal( 1 );
            if ( !(payment.remainder( paymentMethod.getRoundUpValue() ).equals( BigDecimal.ZERO )) )
            {
                payment = payment.add( paymentMethod.getRoundUpValue() );
                payment = new BigDecimal( payment.intValue() );

                while ( !(payment.remainder( paymentMethod.getRoundUpValue() ).equals( BigDecimal.ZERO )) )
                {
                    payment = payment.subtract( dollar );
                }
            }
        }

        return payment;
    }

    public Map<String, List<Drawer>> getDrawers()
    {
        return drawers;
    }
}