import java.util.List;

public class TestPos
{
    public static void main( String[] args )
    {
        PointOfSaleSystem pointOfSaleSystem = new PointOfSaleSystem();
        Reports reports = new Reports();

        List<Sale> sales = pointOfSaleSystem.getRandomSales( 10 );
        reports.generateMemberReport(sales);
    }
}