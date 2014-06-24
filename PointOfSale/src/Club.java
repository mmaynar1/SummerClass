public class Club
{
    private final String name;
    private final int clubNumber;
    private final String id;
    private final String companyId;

    public Club( String name, int clubNumber, String companyId )
    {
        this.name = name;
        this.clubNumber = clubNumber;
        this.companyId = companyId;
        this.id = RandomGenerator.getGuid();
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
