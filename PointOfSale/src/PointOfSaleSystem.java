import java.util.*;

public class PointOfSaleSystem
{
    public void simulateRandomSales( int numberOfSales )
    {
        Map<String, Sale> sales = Database.getSales();
        List<Sale> listOfSales = new ArrayList<Sale>( sales.values() );
        System.out.println( "---------- SALES MADE ----------" );
        Drawer drawer = new Drawer();
        for (Sale sale : listOfSales)
        {
            System.out.println( sale );
            drawer.updateBalance( sale );
        }

        Reports reports = new Reports();
        reports.generateDrawerSummary( drawer );
        reports.generateMemberReport( listOfSales );
        reports.generateSalesItemReport( listOfSales );
        reports.generatePaymentMethodReport( listOfSales );

    }


}