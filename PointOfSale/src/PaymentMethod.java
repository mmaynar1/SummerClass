

import java.math.BigDecimal;

public enum PaymentMethod
{
    cash( "cash", "Cash", 1, new BigDecimal( 500 ), BigDecimal.TEN ),
    visa( "visa", "Visa" ),
    masterCard( "masterCard", "Master Card" ),
    coupon( "coupon", "Coupon" ),
    americanExpress( "AMEX", "American Express" ),
    discover( "DISCOVER", "Discover" ),
    goldBucks( "GBUCK", "Gold Bucks", 1, new BigDecimal( 1000 ), new BigDecimal( 5 ) );

    public static final int MAX_NUMBER_INSTANCES_ALLOWED = 20;

    private final String abcCode;
    private final String name;
    private final int numberOfInstancesAllowed;
    private final BigDecimal startingBalance;
    private final BigDecimal roundUpValue;

    private PaymentMethod( String abcCode, String name, int numberOfInstancesAllowed, BigDecimal startingBalance, BigDecimal roundUpValue )
    {
        this.abcCode = abcCode;
        this.name = name;
        this.numberOfInstancesAllowed = numberOfInstancesAllowed;
        this.startingBalance = startingBalance;
        this.roundUpValue = roundUpValue;
    }

    private PaymentMethod( String abcCode, String name )
    {
        this( abcCode, name, MAX_NUMBER_INSTANCES_ALLOWED, BigDecimal.ZERO, BigDecimal.ZERO );
    }

    public static PaymentMethod getRandomPaymentMethod()
    {
        PaymentMethod randomPaymentMethod = null;

        final int numberOfPaymentMethods = PaymentMethod.values().length;
        int randomOrdinal = RandomGenerator.getInt( 0, numberOfPaymentMethods );
        for (PaymentMethod paymentMethod : PaymentMethod.values())
        {
            if ( paymentMethod.ordinal() == randomOrdinal )
            {
                randomPaymentMethod = paymentMethod;
            }
        }

        return randomPaymentMethod;
    }

    public static String getPaymentMethodName(String abcCode)
    {
        return getPaymentMethod( abcCode ).getName();
    }

    public static PaymentMethod getPaymentMethod( String abcCode )
    {
        PaymentMethod matchingPaymentMethod = null;

        for (PaymentMethod paymentMethod : PaymentMethod.values())
        {
            if ( paymentMethod.getAbcCode().equals( abcCode ) )
            {
                matchingPaymentMethod = paymentMethod;
            }
        }

        return matchingPaymentMethod;
    }

    public boolean isRounded()
    {
        return (getRoundUpValue().compareTo( BigDecimal.ZERO ) > 0);
    }

    public String getAbcCode()
    {
        return abcCode;
    }

    public String getName()
    {
        return name;
    }

    public int getNumberOfInstancesAllowed()
    {
        return numberOfInstancesAllowed;
    }

    public BigDecimal getStartingBalance()
    {
        return startingBalance;
    }

    public BigDecimal getRoundUpValue()
    {
        return roundUpValue;
    }
}