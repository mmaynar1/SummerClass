import java.math.BigDecimal;

public class Tax
{
    private final BigDecimal rate;

    Tax( double rate )
    {
        this( new BigDecimal( rate ) );
    }

    Tax( BigDecimal rate )
    {
        this.rate = rate;
    }


    public BigDecimal getRate()
    {
        return rate;
    }
}
