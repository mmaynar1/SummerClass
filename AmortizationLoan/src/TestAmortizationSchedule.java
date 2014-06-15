import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TestAmortizationSchedule
{
    public static void main( String[] args )
    {
        new TestAmortizationSchedule().test();
    }

    private void test()
    {
        final int amountBorrowed = 135000;
        final BigDecimal annualInterestRate = new BigDecimal( 7 );
        final int termInYears = 1;

        AmortizationSchedule schedule = new AmortizationSchedule( amountBorrowed, annualInterestRate, termInYears );

        if ( schedule.calculate() )
        {
            printSchedule( schedule );
        }
        else
        {
            printErrorMessages( schedule );
        }
    }

    private void printSchedule( AmortizationSchedule schedule )
    {
        final int COLUMN_WIDTH = 20;

        System.out.println( leftJustify( "Payment #", COLUMN_WIDTH ) +
                            leftJustify( "Monthly Payment", COLUMN_WIDTH ) +
                            leftJustify( "Interest", COLUMN_WIDTH ) +
                            leftJustify( "Principal", COLUMN_WIDTH ) +
                            leftJustify( "Total Interest", COLUMN_WIDTH ) +
                            leftJustify( "Total Principal", COLUMN_WIDTH ) +
                            leftJustify( "Balance", COLUMN_WIDTH ) );

        for (MonthlyPaymentDetail detail : schedule.getDetails())
        {
            System.out.println( leftJustify( (detail.getPaymentNumber()) + "", COLUMN_WIDTH ) +
                                leftJustify( formatMoney( detail.getMonthlyPayment() ), COLUMN_WIDTH ) +
                                leftJustify( formatMoney( detail.getInterest() ), COLUMN_WIDTH ) +
                                leftJustify( formatMoney( detail.getPrincipal() ), COLUMN_WIDTH ) +
                                leftJustify( formatMoney( detail.getTotalInterest() ), COLUMN_WIDTH ) +
                                leftJustify( formatMoney( detail.getTotalPrincipal() ), COLUMN_WIDTH ) +
                                leftJustify( formatMoney( detail.getBalance() ), COLUMN_WIDTH ) );
        }
    }

    private void printErrorMessages( AmortizationSchedule schedule )
    {
        for (String message : schedule.getMessages())
        {
            System.out.println( message );
        }
    }

    private String leftJustify( String value, int width )
    {
        return String.format( "%-" + width + "s", value );
    }

    private String formatMoney( BigDecimal money )
    {
        String formattedMoney;

        if ( money == null )
        {
            formattedMoney = "$0.00";
        }
        else
        {
            final DecimalFormat moneyFormat = new DecimalFormat( "$#,##0.00" );
            formattedMoney = moneyFormat.format( money );
        }

        return formattedMoney;
    }
}