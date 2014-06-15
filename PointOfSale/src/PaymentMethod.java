public enum PaymentMethod
{
    CASH( "CASH", "Cash" ), VISA( "VISA", "Visa" ), MC( "MC", "Master Card" ), COUPON( "COUPON", "Coupon" );

    private final String abcCode;
    private final String name;

    PaymentMethod( String abcCode, String name )
    {
        this.abcCode = abcCode;
        this.name = name;
    }

    public String getAbcCode()
    {
        return abcCode;
    }

    public String getName()
    {
        return name;
    }
}
