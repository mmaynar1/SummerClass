import utility.RandomGenerator;

public final class Member
{
    public static final String DELIMITER = ":";
    private final String name;
    private final String id;

    public Member( String name )
    {
        this( name, RandomGenerator.getGuid() );
    }

    public Member( String name, String id )
    {
        this.name = name;
        this.id = id;
    }

    public String getTextRepresentation()
    {
        return (getId() + DELIMITER + getName());
    }


    public Member( Member member )
    {
        this( member.getName(), member.getId() );
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}