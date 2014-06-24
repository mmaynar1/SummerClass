import java.math.BigDecimal;
import java.util.*;

public class Database
{
    private static final Map<String, Member> members = new HashMap<String, Member>();
    private static final Map<String, InventoryItem> inventoryItems = new HashMap<String, InventoryItem>();
    private static final Map<String, Club> clubs = new HashMap<String, Club>();
    private static final Map<String, Company> companies = new HashMap<String, Company>();
    private static Map<String, Sale> sales = new HashMap<String, Sale>();

    private Database()
    {
    }

    static
    {
        initializeMembers();
        initializeInventoryItems();
        initializeCompanies();
        initializeClubs();
    }

    public static BigDecimal getUnitPrice( String inventoryItemId )
    {
        return getInventoryItems().get( inventoryItemId ).getUnitPrice();
    }

    public static String getInventoryItemName( String inventoryItemId )
    {
        return getInventoryItems().get( inventoryItemId ).getName();
    }

    public static Club getClub(String clubId)
    {
        return getClubs().get( clubId );
    }


    public static BigDecimal getInventoryItemTaxRate( String inventoryItemId )
    {
        return getInventoryItems().get( inventoryItemId ).getTax().getRate();
    }

    public static String getMemberName( String memberId )
    {
        return getMembers().get( memberId ).getName();
    }

    public static InventoryItem getInventoryItem(String inventoryItemId)
    {
        return getInventoryItems().get( inventoryItemId );
    }

    public static Member getMember(String memberId)
    {
        return getMembers().get( memberId );
    }


    public static Map<String, SaleItem> getRandomSaleItems( BigDecimal discountRate )
    {
        Map<String, SaleItem> saleItems = new HashMap<String, SaleItem>();
        List<InventoryItem> inventoryItems = getRandomInventoryItems();
        for (InventoryItem inventoryItem : inventoryItems)
        {
            int quantity = RandomGenerator.getInt( 1, 6 );
            SaleItem saleItem = new SaleItem( inventoryItem.getId(), quantity, discountRate );
            saleItems.put( saleItem.getId(), saleItem );
        }
        return saleItems;
    }


    public static List<InventoryItem> getRandomInventoryItems()
    {
        List<InventoryItem> randomInventoryItems = new ArrayList<InventoryItem>();

        int randomNumberOfSaleItems = RandomGenerator.getInt( 1, 5 );

        for (int i = 0; i < randomNumberOfSaleItems; ++i)
        {
            int randomIndex = RandomGenerator.getInt( 0, getInventoryItems().size() );
            randomInventoryItems.add( getListOfInventoryItems().get( randomIndex ) );
        }

        return randomInventoryItems;
    }

    private static void initializeClubs()
    {
        String companyId = getFirstCompanyId();

        List<Club> clubs = new ArrayList<Club>();
        clubs.add( new Club( "Sherwood", 5000, companyId ) );
        clubs.add( new Club( "Conway", 5001, companyId ) );
        clubs.add( new Club( "Benton", 5002, companyId ) );

        for (Club club : clubs)
        {
            getClubs().put( club.getId(), club );
        }

    }

    private static void initializeCompanies()
    {
        List<Company> companies = new ArrayList<Company>();
        companies.add( new Company( "ABC" ) );

        for (Company company : companies)
        {
            getCompanies().put( company.getId(), company );
        }
    }


    private static void initializeInventoryItems()
    {
        List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
        inventoryItems.add( new InventoryItem( "Water", 1.00, new Tax( 0 ) ) );
        inventoryItems.add( new InventoryItem( "Smoothie", 74.73, new Tax( .13 ) ) );
        inventoryItems.add( new InventoryItem( "Muscle Milk", 9.99, new Tax( .05 ) ) );
        inventoryItems.add( new InventoryItem( "Personal Training", 75.73, new Tax( .15 ) ) );
        inventoryItems.add( new InventoryItem( "Tanning", 10, new Tax( .07 ) ) );
        inventoryItems.add( new InventoryItem( "T-Shirt", 12, new Tax( 0 ) ) );
        inventoryItems.add( new InventoryItem( "Towel", 5.68, new Tax( .25 ) ) );
        inventoryItems.add( new InventoryItem( "Protein Bar", 1.25, new Tax( .03 ) ) );
        inventoryItems.add( new InventoryItem( "Vitamins", 10.99, new Tax( .06 ) ) );
        inventoryItems.add( new InventoryItem( "Gloves", 11.95, new Tax( .03 ) ) );

        for (InventoryItem item : inventoryItems)
        {
            getInventoryItems().put( item.getId(), item );
        }
    }

    private static void initializeMembers()
    {
        List<Member> members = new ArrayList<Member>();
        members.add( new Member( "Ken Griffey Jr." ) );
        members.add( new Member( "Bill Cosby" ) );
        members.add( new Member( "Forrest Gump" ) );
        members.add( new Member( "Walter White" ) );
        members.add( new Member( "Babe Ruth" ) );
        members.add( new Member( "Reggie Miller" ) );
        members.add( new Member( "Larry Bird" ) );
        members.add( new Member( "Tiger Woods" ) );
        members.add( new Member( "Taylor Swift" ) );
        members.add( new Member( "George Bush" ) );

        for (Member member : members)
        {
            getMembers().put( member.getId(), member );
        }
    }

    public static List<Member> getListOfMembers()
    {
        return new ArrayList<Member>( getMembers().values() );
    }

    public static String getFirstCompanyId()
    {
        return getListOfCompanies().get( 0 ).getId();
    }

    public static String getCompanyId( String companyName )
    {
        String id = null;
        int count = 0;
        for (Company company : getListOfCompanies())
        {
            if ( company.getName().equals( companyName ) )
            {
                id = company.getId();
                ++count;
            }
        }

        if ( count != 1 )
        {
            throw new RuntimeException( "Duplicate company names; could not get company id" );
        }

        return id;
    }


    public static List<Club> getListOfClubs()
    {
        return new ArrayList<Club>( getClubs().values() );
    }


    public static String getRandomClubId( String companyName )
    {
        String companyId = getCompanyId( companyName );
        List<Club> clubs = new ArrayList<Club>();
        for (Club club : getListOfClubs())
        {
            if ( club.getCompanyId().equals( companyId ) )
            {
                clubs.add( club );
            }
        }

        int randomIndex = RandomGenerator.getInt( 0, clubs.size() );
        return clubs.get( randomIndex ).getId();
    }


    private static List<Company> getListOfCompanies()
    {
        return new ArrayList<Company>( getCompanies().values() );
    }


    private static List<InventoryItem> getListOfInventoryItems()
    {
        return new ArrayList<InventoryItem>( getInventoryItems().values() );
    }

    public static Map<String, Sale> getSales()
    {
        return sales;
    }


    private static Map<String, Member> getMembers()
    {
        return members;
    }

    private static Map<String, InventoryItem> getInventoryItems()
    {
        return inventoryItems;
    }

    private static Map<String, Company> getCompanies()
    {
        return companies;
    }

    public static Map<String, Club> getClubs()
    {
        return clubs;
    }
}