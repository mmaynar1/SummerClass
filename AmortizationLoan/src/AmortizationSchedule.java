import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AmortizationSchedule
{
    public static final int PAYMENTS_PER_YEAR = 12;
    public static final int MONEY_DECIMAL_PLACES = 2;
    public static final int INTEREST_RATE_DECIMAL_PLACES = 50;

    public static final int MINIMUM_LOAN_AMOUNT = 1;
    public static final int MAXIMUM_LOAN_AMOUNT = 9999999;

    public static final double MINIMUM_ANNUAL_INTEREST_RATE = .1;
    public static final int MAXIMUM_ANNUAL_INTEREST_RATE = 50;

    public static final int MINIMUM_TERM_IN_YEARS = 1;
    public static final int MAXIMUM_TERM_IN_YEARS = 50;

    private int amountBorrowed;
    BigDecimal annualInterestRate;
    private int termInYears;
    private BigDecimal monthlyPayment;

    private List<String> messages;
    private List<MonthlyPaymentDetail> details;

    public AmortizationSchedule( int amountBorrowed, BigDecimal annualInterestRate, int termInYears )
    {
        setAmountBorrowed( amountBorrowed );
        setAnnualInterestRate( annualInterestRate );
        setTermInYears( termInYears );

        setMessages( new ArrayList<String>() );
        setDetails( new ArrayList<MonthlyPaymentDetail>() );
    }

    public boolean calculate()
    {
        boolean valid = isValidTerms();
        if ( valid )
        {
            BigDecimal monthlyInterestRate = getMonthlyInterestRate( getAnnualInterestRate() );
            setMonthlyPayment( monthlyInterestRate );

            BigDecimal remainingBalance = new BigDecimal( getAmountBorrowed() );
            BigDecimal totalInterest = BigDecimal.ZERO;
            BigDecimal totalPrincipal = BigDecimal.ZERO;
            BigDecimal monthlyPayment = getMonthlyPayment();

            for (int paymentNumber = 1; paymentNumber <= getTotalNumberOfPayments( getTermInYears() ); ++paymentNumber)
            {
                final BigDecimal interest = remainingBalance.multiply( monthlyInterestRate );
                BigDecimal principal = getMonthlyPayment().subtract( interest );

                totalInterest = totalInterest.add( interest );
                totalPrincipal = totalPrincipal.add( principal );
                remainingBalance = remainingBalance.subtract( principal );

                if ( paymentNumber == getTotalNumberOfPayments( getTermInYears() ) )
                {
                    //Adjustments for last payment fields
                    BigDecimal extraMoney = remainingBalance.abs();
                    monthlyPayment = getMonthlyPayment().subtract( extraMoney );
                    remainingBalance = remainingBalance.add( extraMoney );
                    principal = principal.subtract( extraMoney );
                    totalPrincipal = totalPrincipal.subtract( extraMoney );
                }

                getDetails().add( new MonthlyPaymentDetail( paymentNumber, monthlyPayment, interest, principal, totalInterest, totalPrincipal, remainingBalance ) );
            }
        }

        return valid;
    }

    private void setMonthlyPayment( BigDecimal monthlyInterestRate )
    {
        /*http://www.myamortizationchart.com/articles/how-is-an-amortization-schedule-calculated/

                          monthlyInterestRate * amountBorrowed * ( 1 + monthlyInterestRate )^numberOfPayments
        monthlyPayment = ---------------------------------------------------------------------------------------
                            ( 1 + monthlyInterestRate )^numberOfPayments  - 1

        */

        int numberOfPayments = getTotalNumberOfPayments( getTermInYears() );
        BigDecimal amountBorrowed = new BigDecimal( getAmountBorrowed() );

        BigDecimal numerator = monthlyInterestRate.multiply( monthlyInterestRate.add( BigDecimal.ONE ).pow( numberOfPayments ).multiply( amountBorrowed ) );
        BigDecimal denominator = (monthlyInterestRate.add( BigDecimal.ONE ).pow( numberOfPayments )).subtract( BigDecimal.ONE );
        this.monthlyPayment = numerator.divide( denominator, MONEY_DECIMAL_PLACES, BigDecimal.ROUND_UP );
    }

    private BigDecimal getMonthlyInterestRate( BigDecimal annualInterestRate )
    {
        final int PAYMENTS_PER_YEAR = 12;
        final int DIVISOR = PAYMENTS_PER_YEAR * 100;
        return annualInterestRate.divide( new BigDecimal( DIVISOR ), INTEREST_RATE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
    }

    private boolean isValidTerms()
    {
        boolean validTerms = true;

        if ( getAmountBorrowed() < MINIMUM_LOAN_AMOUNT || getAmountBorrowed() > MAXIMUM_LOAN_AMOUNT )
        {
            getMessages().add( "Amount borrowed must be in the range from " + MINIMUM_LOAN_AMOUNT + " to " + MAXIMUM_LOAN_AMOUNT );
            validTerms = false;
        }

        if ( getAnnualInterestRate().compareTo( new BigDecimal( MINIMUM_ANNUAL_INTEREST_RATE ) ) == -1 || getAnnualInterestRate().compareTo( new BigDecimal( MAXIMUM_ANNUAL_INTEREST_RATE ) ) == 1 )
        {
            getMessages().add( "Annual interest rate must be in the range from " + MINIMUM_ANNUAL_INTEREST_RATE + " to " + MAXIMUM_ANNUAL_INTEREST_RATE + " percent" );
            validTerms = false;
        }

        if ( getTermInYears() < MINIMUM_TERM_IN_YEARS || getTermInYears() > MAXIMUM_TERM_IN_YEARS )
        {
            getMessages().add( "Term must be in the range from " + MINIMUM_TERM_IN_YEARS + " to " + MAXIMUM_TERM_IN_YEARS + " years" );
            validTerms = false;
        }

        return validTerms;
    }

    private int getTotalNumberOfPayments( int termInYears )
    {
        return (termInYears * PAYMENTS_PER_YEAR);
    }

    private BigDecimal getMonthlyPayment()
    {
        return monthlyPayment;
    }

    public List<String> getMessages()
    {
        return messages;
    }

    private void setMessages( List<String> messages )
    {
        this.messages = messages;
    }

    public List<MonthlyPaymentDetail> getDetails()
    {
        return details;
    }

    private void setDetails( List<MonthlyPaymentDetail> details )
    {
        this.details = details;
    }

    private int getAmountBorrowed()
    {
        return amountBorrowed;
    }

    private void setAmountBorrowed( int amountBorrowed )
    {
        this.amountBorrowed = amountBorrowed;
    }

    private BigDecimal getAnnualInterestRate()
    {
        return annualInterestRate;
    }

    private void setAnnualInterestRate( BigDecimal annualInterestRate )
    {
        this.annualInterestRate = annualInterestRate;
    }

    private int getTermInYears()
    {
        return termInYears;
    }

    private void setTermInYears( int termInYears )
    {
        this.termInYears = termInYears;
    }
}