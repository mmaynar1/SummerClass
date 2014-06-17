import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Format
{
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
