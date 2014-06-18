import java.math.BigDecimal;

public enum Tax
{
    None(0), Tax(.05);

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
