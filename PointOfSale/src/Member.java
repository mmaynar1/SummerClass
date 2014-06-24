public final class Member
{
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

    public Member( Member member )
    {
        this(member.getName(), member.getId());
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