package utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Format
{

    public static final int MONEY_DECIMAL_PLACES = 2;
    public static String leftJustify( String value, int width )
    {
        return String.format( "%-" + width + "s", value );
    }

    public static String leftJustify( int value, int width )
    {
        String stringValue = Integer.toString( value );
        return leftJustify( stringValue, width );
    }

    public static String leftJustify( BigDecimal value, int width )
    {
        String stringValue = value.toString();
        return leftJustify( stringValue, width );
    }

    public static String formatMoney( BigDecimal money )
    {
        money = money.setScale( MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
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

    public static String formatRate( BigDecimal rate )
    {
        String formattedRate;

        if ( rate == null )
        {
            formattedRate = "0.00%";
        }
        else
        {
            final DecimalFormat percentageFormat = new DecimalFormat( "#0.00" );
            formattedRate = percentageFormat.format( rate );
        }

        return formattedRate;
    }


    public static String formatPercentage( BigDecimal percentage )
    {
        String formattedPercentage;

        if ( percentage == null )
        {
            formattedPercentage = "0.00%";
        }
        else
        {
            final DecimalFormat percentageFormat = new DecimalFormat( "#,##0.00%" );
            formattedPercentage = percentageFormat.format( percentage );
        }

        return formattedPercentage;
    }

}
