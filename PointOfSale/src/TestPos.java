public class TestPos
{
    public static void main( String[] args )
    {
        Database.initializeDatabase();
        PointOfSaleSystem pointOfSaleSystem = new PointOfSaleSystem();
        pointOfSaleSystem.simulateRandomSales( );
    }
}