import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestReports
{
    public static void main( String[] args )
    {
        new TestReports().generateReports();
    }

    public static final String ABC_CODE_PATTERN = "([A-Za-z]+)";
    public static final String ANY_PATTERN = "(.+)";
    public static final String SALE_ITEMS_PATTERN = "(.+)";//"((!(\\w{32})!(.+)!(\\w{32})!(.+))+)";
    public static final String PAYMENT_DETAILS_PATTERN = "(.+)";//(@[A-Za-z \\-'\\.]+@.+@.+)+";
    public static final String TAX_RATE_PATTERN = "(.+)";
    public static final String MONEY_PATTERN = "(.+)";
    public static final String GUID_PATTERN = "(\\w{32})";
    public static final String NAME_PATTERN = "([A-Za-z \\-'\\.]+)";
    public static final String QUANTITY_PATTERN = "(\\d{1,5})";
    public static final String CLUB_NUMBER_PATTERN = "([\\d]{4})";
    public static final String DELIMITER = ":";

    private static final Map<String, List<Drawer>> DRAWERS = new HashMap<String, List<Drawer>>();

    private String clubsPath;
    private String membersPath;
    private String drawersPath;
    private String inventoryItemsPath;
    private String salesPath;

    public TestReports()
    {
        configureFilePath();
    }

    private void generateReports()
    {
        populateCollections();

        Reports reports = new Reports();
        //todo
        //reports.generateDrawerSummary( getDrawers() );
        List<Sale> sales = new ArrayList<Sale>( Database.SALES.values() );
        reports.generateMemberReport( sales );
        reports.generateSalesItemReport( sales );
        reports.generatePaymentMethodReport( sales );
    }

    private void populateCollections()
    {
        populateMembers();
        populateClubs();
        populateInventoryItems();
        populateDrawers();
        populateSales();
    }

    private void populateDrawers()
    {
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( drawersPath ) );

            Pattern pattern = Pattern.compile( GUID_PATTERN + DELIMITER +
                                               NAME_PATTERN + DELIMITER +
                                               MONEY_PATTERN + DELIMITER +
                                               MONEY_PATTERN + DELIMITER +
                                               MONEY_PATTERN + DELIMITER +
                                               MONEY_PATTERN
                                             );

            String line = reader.readLine();
            while ( !(line == null) )
            {

                Matcher matcher = pattern.matcher( line );

                if ( matcher.matches() )
                {
                    String clubId = matcher.group( 1 );
                    String abcCode = matcher.group( 2 );
                    BigDecimal startingBalance = new BigDecimal( matcher.group( 3 ) );
                    BigDecimal balance = new BigDecimal( matcher.group( 4 ) );
                    BigDecimal moneyIn = new BigDecimal( matcher.group( 5 ) );
                    BigDecimal moneyOut = new BigDecimal( matcher.group( 6 ) );
                    Drawer drawer = new Drawer( abcCode, startingBalance, balance, moneyIn, moneyOut );

                    List<Drawer> drawers = DRAWERS.get( clubId );
                    if ( drawers == null )
                    {
                        drawers = new ArrayList<Drawer>();
                        drawers.add( drawer );
                        DRAWERS.put( clubId, drawers );
                    }
                    else
                    {
                        DRAWERS.get( clubId ).add( drawer );
                    }
                    //System.out.println( drawer.getTextRepresentation() );
                }

                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception exception)
        {
            throw new RuntimeException( "Could not read from drawers file" );
        }
    }


    private void populateInventoryItems()
    {
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( inventoryItemsPath ) );

            Pattern pattern = Pattern.compile( NAME_PATTERN + DELIMITER +
                                               MONEY_PATTERN + DELIMITER +
                                               TAX_RATE_PATTERN + DELIMITER +
                                               GUID_PATTERN );

            String line = reader.readLine();
            while ( !(line == null) )
            {

                Matcher matcher = pattern.matcher( line );

                if ( matcher.matches() )
                {
                    String inventoryItemName = matcher.group( 1 );
                    BigDecimal unitPrice = new BigDecimal( matcher.group( 2 ) );
                    BigDecimal taxRate = new BigDecimal( matcher.group( 3 ) );
                    String id = matcher.group( 4 );
                    InventoryItem inventoryItem = new InventoryItem( inventoryItemName, unitPrice, new Tax( taxRate ), id );
                    Database.INVENTORY_ITEMS.put( id, inventoryItem );
                    System.out.println( inventoryItem.getTextRepresentation() );
                }

                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception exception)
        {
            throw new RuntimeException( "Could not read from members file" );
        }
    }

    private void populateClubs()
    {
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( clubsPath ) );

            Pattern pattern = Pattern.compile( NAME_PATTERN + Club.DELIMITER +
                                               CLUB_NUMBER_PATTERN + Club.DELIMITER +
                                               GUID_PATTERN + Club.DELIMITER +
                                               GUID_PATTERN );

            String line = reader.readLine();
            while ( !(line == null) )
            {

                Matcher matcher = pattern.matcher( line );

                if ( matcher.matches() )
                {
                    String clubName = matcher.group( 1 );
                    int clubNumber = Integer.parseInt( matcher.group( 2 ) );
                    String companyId = matcher.group( 3 );
                    String clubId = matcher.group( 4 );
                    Club club = new Club( clubName, clubNumber, companyId, clubId );
                    Database.CLUBS.put( clubId, club );
                    // System.out.println( club.getTextRepresentation() );
                }

                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception exception)
        {
            throw new RuntimeException( "Could not read from clubs file" );
        }
    }

    private void populateMembers()
    {
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( membersPath ) );

            Pattern pattern = Pattern.compile( GUID_PATTERN + Member.DELIMITER + NAME_PATTERN );

            String line = reader.readLine();
            while ( !(line == null) )
            {

                Matcher matcher = pattern.matcher( line );

                if ( matcher.matches() )
                {
                    String memberId = matcher.group( 1 );
                    String memberName = matcher.group( 2 );
                    Member member = new Member( memberName, memberId );
                    Database.MEMBERS.put( memberId, member );
                    // System.out.println( member.getTextRepresentation() );
                }

                line = reader.readLine();
            }
            reader.close();
        }
        catch (Exception exception)
        {
            throw new RuntimeException( "Could not read from members file" );
        }
    }


    public void populateSales()
    {
        try
        {
            BufferedReader reader = new BufferedReader( new FileReader( salesPath ) );

            Pattern pattern = Pattern.compile( ":(\\w{32}):(\\w{32})!([!\\w\\.]+)#([#\\w\\.]+):(\\w{32}):(\\d{1,2}\\.\\d{2})" );
/*
            List<SaleItem> saleItems = new ArrayList<SaleItem>();
            List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();*/
            String line = reader.readLine();
            while ( line != null )
            {
                Matcher matcher = pattern.matcher( line );

                if ( matcher.matches() )
                {
                    String clubId = matcher.group( 1 );
                    String memberId = matcher.group( 2 );
                    String saleItemLine = matcher.group( 3 );
                    String paymentDetailLine = matcher.group( 4 );
                    String saleId = matcher.group( 5 );
                    BigDecimal saleDiscountRate = new BigDecimal( matcher.group( 6 ) );

                    //System.out.println();
                    // System.out.println();

                    List<SaleItem> saleItems = new ArrayList<SaleItem>(  parseSaleItemsLine( saleItemLine ));
                    List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>(  parsePaymentDetailsToList( paymentDetailLine ));

                    Sale sale = new Sale( clubId, memberId, saleItems, paymentDetails, saleId, saleDiscountRate );
                    Database.SALES.put( sale.getId(), sale );
                    //saleItems.clear();
                    //paymentDetails.clear();


                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException exception)
        {
            throw new RuntimeException( "File could not be read" );
        }
    }

    private List<PaymentDetail> parsePaymentDetailsToList( String paymentDetailLine )
    {
        List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
        String[] parts = paymentDetailLine.split( PaymentDetail.DELIMITER );
        String paymentMethodAbcCode = "";
        BigDecimal cost = BigDecimal.ZERO;
        BigDecimal payment;
        int paymentMethodDelimiterCount = 0;
        for (int i = 0; i < parts.length; ++i)
        {
            ++paymentMethodDelimiterCount;
            switch ( paymentMethodDelimiterCount )
            {
                case 1:
                    paymentMethodAbcCode = parts[i];
                    break;
                case 2:
                    cost = new BigDecimal( parts[i] );
                    break;
                case 3:
                    payment = new BigDecimal( parts[i] );
                    PaymentDetail paymentDetail = new PaymentDetail( paymentMethodAbcCode, cost, payment );
                    paymentDetails.add( paymentDetail );
                    paymentMethodDelimiterCount = 0;
                    break;
                default:
                    throw new RuntimeException( "Invalid delimiter count" );

            }
            //System.out.println();

        }
        return paymentDetails;
    }

    private List<SaleItem> parseSaleItemsLine( String saleItemLine )
    {
        List<SaleItem> saleItems = new ArrayList<SaleItem>();
        String[] parts = saleItemLine.split( SaleItem.DELIMITER );
        String inventoryItemId = "";
        int quantity = 0;
        String saleItemId = "";
        BigDecimal discountRate;
        int saleItemDelimiterCount = 0;
        for (int i = 0; i < parts.length; i++)
        {
            ++saleItemDelimiterCount;
            switch ( saleItemDelimiterCount )
            {
                case 1:
                    inventoryItemId = parts[i];
                    break;
                case 2:
                    quantity = Integer.parseInt( parts[i] );
                    break;
                case 3:
                    saleItemId = parts[i];
                    break;
                case 4:
                    discountRate = new BigDecimal( parts[i] );
                    SaleItem saleItem = new SaleItem( inventoryItemId, quantity, discountRate, saleItemId );
                    saleItems.add( saleItem );
                    saleItemDelimiterCount = 0;
                    break;
                default:
                    throw new RuntimeException( "Invalid delimiter count" );

            }

        }
        return saleItems;
    }

    private void configureFilePath()
    {
        Properties properties = new Properties();
        InputStream input = null;

        try
        {
            input = new FileInputStream( "fileNames.properties" );

            // load a properties file
            properties.load( input );

            membersPath = properties.getProperty( "members" );
            inventoryItemsPath = properties.getProperty( "inventoryItems" );
            clubsPath = properties.getProperty( "clubs" );
            drawersPath = properties.getProperty( "drawers" );
            salesPath = properties.getProperty( "sales" );


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

}
