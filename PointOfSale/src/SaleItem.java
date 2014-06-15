import java.math.BigDecimal;

public final class SaleItem
{
    private final String name;
    private final BigDecimal unitPrice;
    private final String id;

    public SaleItem( String name, double unitPrice )
    {
        this(name, new BigDecimal( unitPrice ));
    }

    public SaleItem( String name, BigDecimal unitPrice )
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.id = RandomGenerator.getGuid();
    }

    public String getName()
    {
        return name;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public String getId()
    {
        return id;
    }
}