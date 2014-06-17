import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//todo should this class just be static methods?
public final class Reports
{

    public static final int SPACES = 20;
    public static final boolean APPEND_MODE = true;

    public void generatePaymentMethodReport( List<Sale> sales )
    {
        List<PaymentMethodReportDetail> paymentMethodReportDetails = getPaymentMethodReportDetails( sales );

        try
        {
            printPaymentMethodReport( paymentMethodReportDetails );
        }
        catch (IOException e)
        {
            throw new RuntimeException( "Could not generate Member Report" );
        }
    }

    private void printPaymentMethodReport( List<PaymentMethodReportDetail> paymentMethodReportDetails ) throws IOException
    {
        String fileName = "src\\paymentMethodReport.txt";
        final String TITLE = "Payment Method Summary";
        final List<String> HEADERS = Arrays.asList( "Payment Method", "Payment Item Count", "Total" );

        printReportHeader( fileName, TITLE, HEADERS );

        File file = getFile( fileName );
        FileWriter fileWriter = new FileWriter( file, APPEND_MODE );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );
        for (PaymentMethodReportDetail detail : paymentMethodReportDetails)
        {
            bufferedWriter.write( Format.leftJustify( detail.getPaymentMethod(), SPACES ) +
                                  Format.leftJustify( detail.getPaymentItemCount(), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( detail.getTotal() ), SPACES ) );
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    private List<PaymentMethodReportDetail> getPaymentMethodReportDetails( List<Sale> sales )
    {
        Map<String, PaymentMethodReportDetail> line = new HashMap<String, PaymentMethodReportDetail>();
        for (Sale sale : sales)
        {
            for (PaymentDetail paymentDetail : sale.getPaymentDetails())
            {
                PaymentMethodReportDetail detail = line.get( paymentDetail.getAbcCode() );
                if ( detail == null )
                {
                    line.put( paymentDetail.getAbcCode(), new PaymentMethodReportDetail( paymentDetail.getName(), paymentDetail.getAmount() ) );
                }
                else
                {
                    detail.update( paymentDetail );
                }
            }

        }
        return new ArrayList<PaymentMethodReportDetail>( line.values() );
    }

    public void generateMemberReport( List<Sale> sales )
    {
        List<MemberReportDetail> memberReportDetails = getMemberReportDetails( sales );

        try
        {
            printMemberReport( memberReportDetails );
        }
        catch (IOException e)
        {
            throw new RuntimeException( "Could not generate Member Report" );
        }
    }

    public void generateSalesItemReport( List<Sale> sales )
    {
        List<SaleItemReportDetail> saleItemReportDetails = getSaleItemReportDetails( sales );
        try
        {
            printSaleItemReport( saleItemReportDetails );
        }
        catch (IOException e)
        {
            throw new RuntimeException( "Could not generate Sale Item Report" );
        }
    }

    private List<SaleItemReportDetail> getSaleItemReportDetails( List<Sale> sales )
    {
        Map<String, SaleItemReportDetail> line = new HashMap<String, SaleItemReportDetail>();
        for (Sale sale : sales)
        {
            for (InventoryItem item : sale.getInventoryItems())
            {
                SaleItemReportDetail detail = line.get( item.getId() );
                if ( detail == null )
                {
                    line.put( item.getId(), new SaleItemReportDetail( item.getName(), item.getUnitPrice() ) );
                }
                else
                {
                    detail.update( item );
                }
            }

        }
        return new ArrayList<SaleItemReportDetail>( line.values() );
    }


    private void printSaleItemReport( List<SaleItemReportDetail> saleItemReportDetails ) throws IOException
    {
        String fileName = "src\\saleItemReport.txt";
        final String TITLE = "Sales Item Summary";
        final List<String> HEADERS = Arrays.asList( "Sale Item", "Sale Item Count", "Total" );

        printReportHeader( fileName, TITLE, HEADERS );

        File file = getFile( fileName );
        FileWriter fileWriter = new FileWriter( file, APPEND_MODE );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );
        for (SaleItemReportDetail detail : saleItemReportDetails)
        {
            bufferedWriter.write( Format.leftJustify( detail.getSaleItem(), SPACES ) +
                                  Format.leftJustify( detail.getSaleItemCount(), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( detail.getTotal() ), SPACES ) );
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    private void printReportHeader( String fileName, String title, List<String> headers ) throws IOException
    {
        File file = getFile( fileName );

        FileWriter fileWriter = new FileWriter( file );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

        bufferedWriter.write( title );
        bufferedWriter.newLine();
        bufferedWriter.newLine();

        for (String header : headers)
        {
            bufferedWriter.write( Format.leftJustify( header, SPACES ) );
        }

        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    private void printMemberReport( List<MemberReportDetail> memberReportDetails ) throws IOException
    {
        String fileName = "src\\memberReport.txt";
        final String TITLE = "Sales Summary";
        final List<String> HEADERS = Arrays.asList( "Member", "Sales Count", "Sale Item Count", "Total" );

        printReportHeader( fileName, TITLE, HEADERS );

        File file = getFile( fileName );
        FileWriter fileWriter = new FileWriter( file, APPEND_MODE );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );
        for (MemberReportDetail detail : memberReportDetails)
        {
            bufferedWriter.write( Format.leftJustify( detail.getMemberName(), SPACES ) +
                                  Format.leftJustify( detail.getSalesCount(), SPACES ) +
                                  Format.leftJustify( detail.getSaleItemCount(), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( detail.getTotal() ), SPACES ) );
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

    private List<MemberReportDetail> getMemberReportDetails( List<Sale> sales )
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
        return new ArrayList<MemberReportDetail>( line.values() );
    }

}