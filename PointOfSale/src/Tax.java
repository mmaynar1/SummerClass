import java.math.BigDecimal;

public class Tax
{
    private final BigDecimal rate;

    Tax( double rate )
    {
        this.rate = new BigDecimal(  rate );
    }

    public BigDecimal getRate()
    {
        return rate;
    }
}
