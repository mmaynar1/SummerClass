import java.math.BigDecimal;

public class RandomClass
{
    private String title;
    private Boolean caption;  // different type than in ReflectionDemo
    private Integer index;
    private BigDecimal payment;
    private String junk;
    private BigDecimal otherJunk;

    public RandomClass()
    {
    }

    @Override
    public String toString()
    {
        return "RandomClass{" +
               "title='" + getTitle() + '\'' +
               ", caption='" + getCaption() + '\'' +
               ", index=" + getIndex() +
               ", payment=" + getPayment() +
               ", junk='" + getJunk() + '\'' +
               ", otherJunk=" + getOtherJunk() +
               '}';
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public Boolean getCaption()
    {
        return caption;
    }

    public void setCaption( Boolean caption )
    {
        this.caption = caption;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex( Integer index )
    {
        this.index = index;
    }

    public BigDecimal getPayment()
    {
        return payment;
    }

    public void setPayment( BigDecimal payment )
    {
        this.payment = payment;
    }

    public String getJunk()
    {
        return junk;
    }

    public void setJunk( String junk )
    {
        this.junk = junk;
    }

    public BigDecimal getOtherJunk()
    {
        return otherJunk;
    }

    public void setOtherJunk( BigDecimal otherJunk )
    {
        this.otherJunk = otherJunk;
    }
}
