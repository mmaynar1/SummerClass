import utility.RandomGenerator;

public class Club
{
   public static final String DELIMITER = ":";

    private final String name;
    private final int clubNumber;
    private final String id;
    private final String companyId;

    public Club( String name, int clubNumber, String companyId, String id )
    {
        this.name = name;
        this.clubNumber = clubNumber;
        this.companyId = companyId;
        this.id = id;
    }

    public Club( String name, int clubNumber, String companyId )
    {
        this( name, clubNumber, companyId, RandomGenerator.getGuid() );
    }

    public Club(String id)
    {
        this(Database.getClub( id ));
    }

    public Club( Club club )
    {
        this( club.getName(), club.getClubNumber(), club.getCompanyId(), club.getId() );
    }

    public String getTextRepresentation()
    {
        return getName() + DELIMITER +
               getClubNumber() + DELIMITER +
               getCompanyId() + DELIMITER +
               getId();
    }

    public String getName()
    {
        return name;
    }

    public int getClubNumber()
    {
        return clubNumber;
    }

    public String getId()
    {
        return id;
    }

    public String getCompanyId()
    {
        return companyId;
    }
}
