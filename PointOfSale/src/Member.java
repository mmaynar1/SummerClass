public final class Member
{
    private final String name;
    private final String id;

    public Member( String name )
    {
        this.name = name;
        this.id = RandomGenerator.getGuid();
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