import java.math.BigDecimal;

public class MonthlyPaymentDetail
{
    final private int paymentNumber;
    final BigDecimal monthlyPayment;
    final private BigDecimal interest;
    final private BigDecimal principal;
    final private BigDecimal totalPrincipal;
    final private BigDecimal totalInterest;
    final private BigDecimal balance;

    public MonthlyPaymentDetail( int paymentNumber,
                                 BigDecimal monthlyPayment,
                                 BigDecimal interest,
                                 BigDecimal principal,
                                 BigDecimal totalInterest,
                                 BigDecimal totalPrincipal,
                                 BigDecimal balance )
    {
        this.paymentNumber = paymentNumber;
        this.monthlyPayment = monthlyPayment;
        this.interest = interest;
        this.principal = principal;
        this.totalPrincipal = totalPrincipal;
        this.totalInterest = totalInterest;
        this.balance = balance;
    }

    public int getPaymentNumber()
    {
        return paymentNumber;
    }

    public BigDecimal getInterest()
    {
        return interest;
    }

    public BigDecimal getPrincipal()
    {
        return principal;
    }

    public BigDecimal getTotalPrincipal()
    {
        return totalPrincipal;
    }

    public BigDecimal getTotalInterest()
    {
        return totalInterest;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public BigDecimal getMonthlyPayment()
    {
        return monthlyPayment;
    }
}