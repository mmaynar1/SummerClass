

import utility.FileSupport;
import utility.Format;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Reports
{

    public static final int SPACES = 20;
    public static final boolean APPEND_MODE = true;
    public static final String salesFile = "src\\sales.txt";
    private String drawerSummaryReportPath;
    private String paymentMethodReportPath;
    private String saleItemReportPath;
    private String memberReportPath;


    Reports()
    {
        configureFilePath();
    }


/*    public static void main( String[] args )
    {

        new Reports().createSales();
    }*/

    private void configureFilePath()
    {
        Properties properties = new Properties();
        InputStream input = null;

        try
        {
            input = new FileInputStream( "fileNames.properties" );

            // load a properties file
            properties.load( input );

            drawerSummaryReportPath = properties.getProperty( "drawerSummaryReport" );
            paymentMethodReportPath = properties.getProperty( "paymentMethodReport" );
            saleItemReportPath = properties.getProperty( "saleItemReport" );
            memberReportPath = properties.getProperty( "memberReport" );

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if ( input != null )
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    public void createSales()
    {
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( salesFile ) );
            Scanner scanner = new Scanner( reader );


            final String guidPattern = "(\\w{32})";

            Pattern pattern = Pattern.compile( guidPattern + Sale.DELIMITER + guidPattern + ".*" );

            int count = 1;

            while ( scanner.hasNext() )
            {
                String line = scanner.next();
                Matcher matcher = pattern.matcher( line );

                if ( matcher.matches() )
                {
                    String clubId = matcher.group( 1 );
                    String memberId = matcher.group( 2 );
                    Club club = new Club( clubId );
                    Member member = Database.getMember( memberId );
                    System.out.println( club.getClubNumber() + " " + club.getName() );
                    System.out.println( member.getName() );

                }


                System.out.println( count + "  " + line );
                ++count;
                System.out.println();
                System.out.println();
            }
        }
        catch (IOException exception)
        {
            throw new RuntimeException( "File could not be read" );
        }
    }


    public void createSalesFile( List<Sale> sales )
    {
        try
        {
            String fileName = salesFile;
            FileSupport.clearFile( fileName );
            File file = FileSupport.getFile( fileName );
            FileWriter fileWriter = new FileWriter( file, APPEND_MODE );
            BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );

            for (Sale sale : sales)
            {
                addSaleToFile( sale, bufferedWriter );
            }

            bufferedWriter.close();
        }
        catch (Exception exception)
        {
            throw new RuntimeException( "Could not write to sales file" );
        }
    }

    private void addSaleToFile( Sale sale, BufferedWriter writer ) throws IOException
    {
        writer.write( sale.getTextRepresentation() );
        writer.newLine();
    }

    public void generateDrawerSummary( Map<String, List<Drawer>> drawers )
    {

        String fileName = drawerSummaryReportPath;
        final List<String> HEADERS = Arrays.asList( "Payment Method", "Starting Balance", "In", "Out", "Ending Balance", "Net" );

        try
        {

            FileSupport.clearFile( fileName );

            File file = FileSupport.getFile( fileName );
            FileWriter fileWriter = new FileWriter( file, APPEND_MODE );
            BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );


            BigDecimal companyStartingBalance = BigDecimal.ZERO;
            BigDecimal companyMoneyIn = BigDecimal.ZERO;
            BigDecimal companyMoneyOut = BigDecimal.ZERO;
            BigDecimal companyBalance = BigDecimal.ZERO;
            BigDecimal companyGrandTotal = BigDecimal.ZERO;
            for (String clubId : drawers.keySet())
            {
                List<Drawer> clubDrawers = drawers.get( clubId );

                String title = "Drawer Summary - " + Database.getClub( clubId ).getClubNumber() + " " + Database.getClub( clubId ).getName();
                bufferedWriter.newLine();

                printReportHeader( title, HEADERS, bufferedWriter );

                Collections.sort( clubDrawers, new DrawerNameComparator() );
                BigDecimal startingBalance = BigDecimal.ZERO;
                BigDecimal moneyIn = BigDecimal.ZERO;
                BigDecimal moneyOut = BigDecimal.ZERO;
                BigDecimal balance = BigDecimal.ZERO;
                BigDecimal grandTotal = BigDecimal.ZERO;
                for (Drawer drawer : clubDrawers)
                {
                    bufferedWriter.write( Format.leftJustify( PaymentMethod.getPaymentMethodName( drawer.getPaymentMethodAbcCode() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( drawer.getStartingBalance() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( drawer.getMoneyIn() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( drawer.getMoneyOut() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( drawer.getBalance() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( drawer.getBalance().subtract( drawer.getStartingBalance() ) ), SPACES ) );

                    bufferedWriter.newLine();
                    startingBalance = startingBalance.add( drawer.getStartingBalance() );
                    moneyIn = moneyIn.add( drawer.getMoneyIn() );
                    moneyOut = moneyOut.add( drawer.getMoneyOut() );
                    balance = balance.add( drawer.getBalance() );
                    grandTotal = grandTotal.add( drawer.getBalance().subtract( drawer.getStartingBalance() ) );
                }

                companyStartingBalance = companyStartingBalance.add( startingBalance );
                companyMoneyIn = companyMoneyIn.add( moneyIn );
                companyMoneyOut = companyMoneyOut.add( moneyOut );
                companyBalance = companyBalance.add( balance );
                companyGrandTotal = companyGrandTotal.add( grandTotal );
                bufferedWriter.write( Format.leftJustify( "Totals: ", SPACES ) +
                                      Format.leftJustify( Format.formatMoney( startingBalance ), SPACES ) +
                                      Format.leftJustify( Format.formatMoney( moneyIn ), SPACES ) +
                                      Format.leftJustify( Format.formatMoney( moneyOut ), SPACES ) +
                                      Format.leftJustify( Format.formatMoney( balance ), SPACES ) +
                                      Format.leftJustify( Format.formatMoney( grandTotal ), SPACES ) );
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write( Format.leftJustify( "Company Totals: ", SPACES ) +
                                  Format.leftJustify( Format.formatMoney( companyStartingBalance ), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( companyMoneyIn ), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( companyMoneyOut ), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( companyBalance ), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( companyGrandTotal ), SPACES ) );
            bufferedWriter.close();
        }

        catch (FileNotFoundException exception)
        {
            throw new RuntimeException( "File not found" );
        }
        catch (IOException exception)
        {
            throw new RuntimeException( "Could not generate Drawer Summary" );
        }



    }

    public void generatePaymentMethodReport( List<Sale> sales )
    {
        List<PaymentMethodReportDetail> paymentMethodReportDetails = getPaymentMethodReportDetails( sales );

        try
        {
            printPaymentMethodReport( paymentMethodReportDetails );
        }
        catch (IOException exception)
        {
            throw new RuntimeException( "Could not generate Member Report" );
        }
    }

    private void printPaymentMethodReport( List<PaymentMethodReportDetail> paymentMethodReportDetails ) throws IOException
    {
        String fileName = paymentMethodReportPath;

        Set<String> clubIds = getClubIds( paymentMethodReportDetails );

        final List<String> HEADERS = Arrays.asList( "Payment Method", "Payment Item Count", "Total" );
        FileSupport.clearFile( fileName );

        File file = FileSupport.getFile( fileName );
        FileWriter fileWriter = new FileWriter( file, APPEND_MODE );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );


        int companyPaymentItemCount = 0;
        BigDecimal companyGrandTotal = BigDecimal.ZERO;
        for (String clubId : clubIds)
        {
            final String title = "Payment Method Summary - " + Database.getClub( clubId ).getClubNumber() + " " + Database.getClub( clubId ).getName();
            printReportHeader( title, HEADERS, bufferedWriter );

            int paymentItemCount = 0;
            BigDecimal grandTotal = BigDecimal.ZERO;
            for (PaymentMethodReportDetail detail : paymentMethodReportDetails)
            {

                if ( clubId.equals( detail.getClubId() ) )
                {
                    bufferedWriter.write( Format.leftJustify( detail.getPaymentMethod(), SPACES ) +
                                          Format.leftJustify( detail.getPaymentItemCount(), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( detail.getTotal() ), SPACES ) );
                    bufferedWriter.newLine();
                    paymentItemCount += detail.getPaymentItemCount();
                    grandTotal = grandTotal.add( detail.getTotal() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
                }
            }
            bufferedWriter.write( Format.leftJustify( "Totals: ", SPACES ) +
                                  Format.leftJustify( paymentItemCount, SPACES ) +
                                  Format.leftJustify( Format.formatMoney( grandTotal ), SPACES ) );
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            companyPaymentItemCount += paymentItemCount;
            companyGrandTotal = companyGrandTotal.add( grandTotal );
        }
        bufferedWriter.newLine();
        bufferedWriter.write( Format.leftJustify( "Company Totals: ", SPACES ) +
                              Format.leftJustify( companyPaymentItemCount, SPACES ) +
                              Format.leftJustify( Format.formatMoney( companyGrandTotal ), SPACES ) );
        bufferedWriter.close();
    }

    private Set<String> getClubIds( List<PaymentMethodReportDetail> paymentMethodReportDetails )
    {
        Set<String> clubIds = new HashSet<String>();
        for (PaymentMethodReportDetail detail : paymentMethodReportDetails)
        {
            clubIds.add( detail.getClubId() );
        }
        return clubIds;
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
                    line.put( paymentDetail.getAbcCode(), new PaymentMethodReportDetail( sale.getClubId(), paymentDetail.getName(), paymentDetail.getCost() ) );
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
            for (SaleItem saleItem : sale.getSaleItems())
            {
                SaleItemReportDetail detail = line.get( saleItem.getInventoryItemId() );
                String inventoryItemName = saleItem.getInventoryItemName();
                BigDecimal unitPrice = Database.getUnitPrice( saleItem.getInventoryItemId() );
                BigDecimal tax = saleItem.getTax();
                if ( detail == null )
                {
                    line.put( saleItem.getInventoryItemId(), new SaleItemReportDetail( sale.getClubId(), inventoryItemName, saleItem.getQuantity(), unitPrice, saleItem.getExtendedPrice(), saleItem.getDiscount(), saleItem.getSubTotal(), tax, saleItem.getTaxRate() ) );
                }
                else
                {
                    detail.update( saleItem );
                }
            }

        }
        return new ArrayList<SaleItemReportDetail>( line.values() );
    }

    private void printSaleItemReport( List<SaleItemReportDetail> saleItemReportDetails ) throws IOException
    {
        String fileName = saleItemReportPath;

        final List<String> HEADERS = Arrays.asList( "Name", "Quantity", "Unit Price", "Extended Price", "Discount", "Sub Total", "Tax Rate", "Tax", "Total" );
        FileSupport.clearFile( fileName );


        File file = FileSupport.getFile( fileName );
        FileWriter fileWriter = new FileWriter( file, APPEND_MODE );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );


        Set<String> clubIds = new HashSet<String>();
        for (SaleItemReportDetail detail : saleItemReportDetails)
        {
            clubIds.add( detail.getClubId() );
        }

        int companyQuantity = 0;
        BigDecimal companyExtendedPrice = BigDecimal.ZERO;
        BigDecimal companyDiscount = BigDecimal.ZERO;
        BigDecimal companySubTotal = BigDecimal.ZERO;
        BigDecimal companyTax = BigDecimal.ZERO;
        BigDecimal companyGrandTotal = BigDecimal.ZERO;

        for (String clubId : clubIds)
        {
            String title = "Sales Item Summary - " + Database.getClub( clubId ).getClubNumber() + " " + Database.getClub( clubId ).getName();
            printReportHeader( title, HEADERS, bufferedWriter );
            int quantity = 0;
            BigDecimal extendedPrice = BigDecimal.ZERO;
            BigDecimal discount = BigDecimal.ZERO;
            BigDecimal subTotal = BigDecimal.ZERO;
            BigDecimal tax = BigDecimal.ZERO;
            BigDecimal grandTotal = BigDecimal.ZERO;
            for (SaleItemReportDetail detail : saleItemReportDetails)
            {
                if ( clubId.equals( detail.getClubId() ) )
                {
                    bufferedWriter.write( Format.leftJustify( detail.getInventoryItemName(), SPACES ) +
                                          Format.leftJustify( detail.getSaleItemCount(), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( detail.getUnitPrice() ), SPACES ) +

                                          Format.leftJustify( Format.formatMoney( detail.getExtendedPrice() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( detail.getDiscount() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( detail.getSubTotal() ), SPACES ) +
                                          Format.leftJustify( Format.formatPercentage( detail.getTaxRate() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( detail.getTax() ), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( detail.getTotal() ), SPACES ) );

                    bufferedWriter.newLine();

                    quantity = quantity + detail.getSaleItemCount();
                    extendedPrice = extendedPrice.add( detail.getExtendedPrice() );
                    discount = discount.add( detail.getDiscount() );
                    subTotal = subTotal.add( detail.getSubTotal() );
                    tax = tax.add( detail.getTax() );
                    grandTotal = grandTotal.add( detail.getTotal() ).setScale( PointOfSaleSystem.MONEY_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP );
                }
            }
            bufferedWriter.write( Format.leftJustify( "Totals: ", SPACES ) +
                                  Format.leftJustify( quantity, SPACES ) +
                                  Format.leftJustify( "", SPACES ) +
                                  Format.leftJustify( Format.formatMoney( extendedPrice ), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( discount ), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( subTotal ), SPACES ) +
                                  Format.leftJustify( "", SPACES ) +
                                  Format.leftJustify( Format.formatMoney( tax ), SPACES ) +
                                  Format.leftJustify( Format.formatMoney( grandTotal ), SPACES ) );
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            companyQuantity += quantity;
            companyExtendedPrice = companyExtendedPrice.add( extendedPrice );
            companyDiscount = companyDiscount.add( discount );
            companySubTotal = companySubTotal.add( subTotal );
            companyTax = companyTax.add( tax );
            companyGrandTotal = companyGrandTotal.add( grandTotal );
        }
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write( Format.leftJustify( "Company Totals: ", SPACES ) +
                              Format.leftJustify( companyQuantity, SPACES ) +
                              Format.leftJustify( "", SPACES ) +
                              Format.leftJustify( Format.formatMoney( companyExtendedPrice ), SPACES ) +
                              Format.leftJustify( Format.formatMoney( companyDiscount ), SPACES ) +
                              Format.leftJustify( Format.formatMoney( companySubTotal ), SPACES ) +
                              Format.leftJustify( "", SPACES ) +
                              Format.leftJustify( Format.formatMoney( companyTax ), SPACES ) +
                              Format.leftJustify( Format.formatMoney( companyGrandTotal ), SPACES ) );
        bufferedWriter.close();
    }

    private void printReportHeader( String title, List<String> headers, BufferedWriter bufferedWriter ) throws IOException
    {
        bufferedWriter.write( title );
        bufferedWriter.newLine();

        for (String header : headers)
        {
            bufferedWriter.write( Format.leftJustify( header, SPACES ) );
        }

        bufferedWriter.newLine();
    }

    private static class MemberNameComparator implements Comparator<MemberReportDetail>
    {
        @Override
        public int compare( MemberReportDetail first, MemberReportDetail second )
        {
            return first.getMemberName().compareTo( second.getMemberName() );
        }

    }

    private static class DrawerNameComparator implements Comparator<Drawer>
    {
        @Override
        public int compare( Drawer first, Drawer second )
        {
            String firstName = PaymentMethod.getPaymentMethodName( first.getPaymentMethodAbcCode() );
            String secondName = PaymentMethod.getPaymentMethodName( second.getPaymentMethodAbcCode() );
            return firstName.compareTo( secondName );
        }

    }

    /*public void clearFile( String fileName ) throws IOException
    {
        File file = getFile( fileName );
        FileWriter fileWriter = new FileWriter( file );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );
        bufferedWriter.flush();
        bufferedWriter.close();
    }*/


    private void printMemberReport( List<MemberReportDetail> memberReportDetails ) throws IOException
    {
        String fileName = memberReportPath;

        final List<String> HEADERS = Arrays.asList( "Member", "Sales Count", "Sale Item Count", "Total" );

        Set<String> clubIds = new HashSet<String>();
        for (MemberReportDetail detail : memberReportDetails)
        {
            clubIds.add( detail.getClubId() );
        }


        FileSupport.clearFile( fileName );
        File file = FileSupport.getFile( fileName );
        FileWriter fileWriter = new FileWriter( file, APPEND_MODE );
        BufferedWriter bufferedWriter = new BufferedWriter( fileWriter );
        int companySalesCount = 0;
        int companySalesItemCount = 0;
        BigDecimal companyGrandTotal = BigDecimal.ZERO;
        for (String clubId : clubIds)
        {
            String title = "Member Sales Summary - " + Database.getClub( clubId ).getClubNumber() + " " + Database.getClub( clubId ).getName();

            printReportHeader( title, HEADERS, bufferedWriter );
            int salesCount = 0;
            int salesItemCount = 0;
            BigDecimal grandTotal = BigDecimal.ZERO;

            Collections.sort( memberReportDetails, new MemberNameComparator() );
            for (MemberReportDetail detail : memberReportDetails)
            {

                if ( clubId.equals( detail.getClubId() ) )
                {
                    bufferedWriter.write( Format.leftJustify( detail.getMemberName(), SPACES ) +
                                          Format.leftJustify( detail.getSalesCount(), SPACES ) +
                                          Format.leftJustify( detail.getSaleItemCount(), SPACES ) +
                                          Format.leftJustify( Format.formatMoney( detail.getTotal() ), SPACES ) );
                    bufferedWriter.newLine();
                    salesCount += detail.getSalesCount();
                    salesItemCount += detail.getSaleItemCount();
                    grandTotal = grandTotal.add( detail.getTotal() );
                }
            }
            bufferedWriter.write( Format.leftJustify( "Totals: ", SPACES ) +
                                  Format.leftJustify( salesCount, SPACES ) +
                                  Format.leftJustify( salesItemCount, SPACES ) +
                                  Format.leftJustify( Format.formatMoney( grandTotal ), SPACES ) );
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            companySalesCount += salesCount;
            companySalesItemCount += salesItemCount;
            companyGrandTotal = companyGrandTotal.add( grandTotal );
        }
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write( Format.leftJustify( "Company Totals: ", SPACES ) +
                              Format.leftJustify( companySalesCount, SPACES ) +
                              Format.leftJustify( companySalesItemCount, SPACES ) +
                              Format.leftJustify( Format.formatMoney( companyGrandTotal ), SPACES ) );
        bufferedWriter.close();
    }

   /* private File getFile( String filePath ) throws IOException
    {
        File file = new File( filePath );

        // if file doesn't exist, then create it
        if ( !file.exists() )
        {
            file.createNewFile();
        }
        return file;
    }*/

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