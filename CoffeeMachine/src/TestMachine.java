public class TestMachine
{
    public static void main( String[] args )
    {
        TestMachine machine = new TestMachine();
        machine.test();
    }

    public void test()
    {
        Machine machine = new Machine();
        machine.getCommand( Machine.Buttons.payQuarter );
        machine.getCommand( Machine.Buttons.payQuarter );
        machine.getCommand( Machine.Buttons.orderBlack );

    }

}
