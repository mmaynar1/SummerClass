import utility.RandomGenerator;

public class Company
{
    private final String name;
    private final String id;

    public Company( String name )
    {
        this.name = name;
        this.id = RandomGenerator.getGuid();
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }
}
