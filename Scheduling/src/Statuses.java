public enum Statuses
{

    pending("PEN"), complete("COM"), cancelled("CAN");

    //abcCode must match the one in the statuses table
    private final String abcCode;

    private Statuses(String abcCode)
    {
        this.abcCode = abcCode;
    }

    public String getAbcCode()
    {
        return abcCode;
    }
}
