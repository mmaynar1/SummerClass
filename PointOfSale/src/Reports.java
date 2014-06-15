import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

//todo should this class just be static methods?
public final class Reports
{
    public void generateMemberReport( List<Sale> sales )
    {
        Map<String, MemberReportDetail> memberReportDetails = getMemberReportDetails( sales );

        try
        {
            printMemberReport( memberReportDetails );
        }
        catch (IOException e)
        {
            throw new RuntimeException( "Could not generate Member Report" );
        }
    }

    private void printMemberReport( Map<String, MemberReportDetail> memberReportDetails ) throws IOException
    {
        File file = getFile( "src\\memberReport.txt" );

        FileWriter fileWriter = new FileWriter( file.getAbsoluteFile() );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

        final String TITLE = "Sales Summary";
        final List<String> HEADER = Arrays.asList( "Member", "Sales Count", "Sale Item Count", "Total" );
        final int SPACES = 20;

        bufferedWriter.write( TITLE );
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        for (String header : HEADER)
        {
            bufferedWriter.write( leftJustify( header, SPACES ) );
        }

        bufferedWriter.newLine();

        for (MemberReportDetail detail : memberReportDetails.values())
        {
            bufferedWriter.write( leftJustify( detail.getMemberName(), SPACES ) +
                                  leftJustify( detail.getSalesCount(), SPACES ) +
                                  leftJustify( detail.getSaleItemCount(), SPACES ) +
                                  leftJustify( formatMoney( detail.getTotal() ), SPACES ) );
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    private File getFile( String filePath ) throws IOException
    {
        File file = new File( filePath );

        // if file doesn't exist, then create it
        if ( !file.exists() )
        {
            file.createNewFile();
        }
        return file;
    }

    private Map<String, MemberReportDetail> getMemberReportDetails( List<Sale> sales )
    {
        Map<String, MemberReportDetail> line = new HashMap<String, MemberReportDetail>();
        for (Sale sale : sales)
        {
            MemberReportDetail detail = line.get( sale.getMemberId() );
            if ( detail == null )
            {
                line.put( sale.getMemberId(), new MemberReportDetail( sale ) );
            }
            else
            {
                detail.update( sale );
            }
        }
        return line;
    }

    private static String leftJustify( String value, int width )
    {
        return String.format( "%-" + width + "s", value );
    }

    private static String leftJustify( int value, int width )
    {
        String stringValue = Integer.toString( value );
        return leftJustify( stringValue, width );
    }

    private static String leftJustify( BigDecimal value, int width )
    {
        String stringValue = value.toString();
        return leftJustify( stringValue, width );
    }

    private static String formatMoney( BigDecimal money )
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