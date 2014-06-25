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

    public static final String TAX_RATE_PATTERN = "(.+)";
    public static final String MONEY_PATTERN = "(.+)";
    public static final String GUID_PATTERN = "(\\w{32})";
    public static final String NAME_PATTERN = "([A-Za-z \\-'\\.]+)";
    public static final String CLUB_NUMBER_PATTERN = "([\\d]{4})";
    public static final String DELIMITER = ":";

    private static final Map<String, Member> MEMBERS = new HashMap<String, Member>();
    private static final Map<String, InventoryItem> INVENTORY_ITEMS = new HashMap<String, InventoryItem>();
    private static final Map<String, Club> CLUBS = new HashMap<String, Club>();
    private static final Map<String, Sale> SALES = new HashMap<String, Sale>();
    private static final Map<String, List<Drawer>> DRAWERS = new HashMap<String, List<Drawer>>();

    private String clubsPath;
    private String membersPath;
    private String drawersPath;
    private String inventoryItemsPath;

    public TestReports()
    {
        configureFilePath();
    }

    private void generateReports()
    {
        populateCollections();
    }

    private void populateCollections()
    {
        populateMembers();
        populateClubs();
        populateInventoryItems();
        populateDrawers();
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
                        drawers = new ArrayList<Drawer>(  );
                        drawers.add( drawer );
                        DRAWERS.put( clubId, drawers );
                    }
                    else
                    {
                        DRAWERS.get( clubId ).add( drawer );
                    }
                    System.out.println( drawer.getTextRepresentation() );
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
                    INVENTORY_ITEMS.put( id, inventoryItem );
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
                    CLUBS.put( clubId, club );
                    System.out.println( club.getTextRepresentation() );
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
                    MEMBERS.put( memberId, member );
                    System.out.println( member.getTextRepresentation() );
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


    /*  public void createSales()
          {
              try
              {
                  BufferedReader reader = new BufferedReader( new FileReader( salesFile ) );
                  Scanner scanner = new Scanner( reader );


                  final String GUID_PATTERN = "(\\w{32})";

                  Pattern pattern = Pattern.compile( GUID_PATTERN + Sale.DELIMITER + GUID_PATTERN + ".*" );

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

            membersPath = properties.getProperty( "members" );
            inventoryItemsPath = properties.getProperty( "inventoryItems" );
            clubsPath = properties.getProperty( "clubs" );
            drawersPath = properties.getProperty( "drawers" );


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
