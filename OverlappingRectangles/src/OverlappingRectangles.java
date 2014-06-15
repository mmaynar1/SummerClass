import java.awt.*;

public class OverlappingRectangles
{
    public static void main( String[] args )
    {
        int[] xUpperLeft = {1, 3, 4, 2, 4, 3, 4, 1, -4, -6, 2, 3, 1, -2, 1, 2, 0, 1, 2, 12, 3, 6, 2, 3};
        int[] yUpperLeft = {8, 5, 10, 7, 10, 5, 10, 8, 4, 0, 7, 5, 8, 3, 8, 7, 10, 8, 8, 6, 12, 6, 7, 6};
        int[] xLowerRight = {6, 10, 8, 9, 8, 10, 8, 6, 4, -1, 9, 10, 6, 7, 6, 5, 10, 6, 4, 6, 6, 10, 9, 9};
        int[] yLowerRight = {1, 2, 4, 6, 4, 2, 4, 1, -4, -3, 6, 2, 1, -2, 1, 2, 0, 1, 6, 1, 6, 0, 6, 5};

        int[] overlappingArea = {9, 4, 4, 8, 9, 0, 10, 15, 35, 0, 0, 0};

        int i = 0;
        for (double area : overlappingArea)
        {
            Rectangle rectangle1 = new Rectangle( new Point( xUpperLeft[i], yUpperLeft[i] ), new Point( xLowerRight[i], yLowerRight[i] ) );
            Rectangle rectangle2 = new Rectangle( new Point( xUpperLeft[i + 1], yUpperLeft[i + 1] ), new Point( xLowerRight[i + 1], yLowerRight[i + 1] ) );

            if ( area == getOverlappingArea( rectangle1, rectangle2 ) )
            {
                System.out.println( getOverlappingArea( rectangle1, rectangle2 ) );
            }
            i += 2;
        }
    }

    private static double getOverlappingArea( Rectangle rectangle1, Rectangle rectangle2 )
    {
        double horizontalLength = getHorizontalLength( rectangle1, rectangle2 );

        double verticalLength = getVerticalLength( rectangle1, rectangle2 );

        double area = Math.max( 0, horizontalLength * verticalLength );

        return area;
    }

    private static double getVerticalLength( Rectangle rectangle1, Rectangle rectangle2 )
    {
        double topY = Math.min( rectangle1.getTopLeft().getY(), rectangle2.getTopLeft().getY() );
        double bottomY = Math.max( rectangle1.getBottomRight().getY(), rectangle2.getBottomRight().getY() );
        return topY - bottomY;
    }

    private static double getHorizontalLength( Rectangle rectangle1, Rectangle rectangle2 )
    {
        double rightX = Math.min( rectangle1.getBottomRight().getX(), rectangle2.getBottomRight().getX() );
        double leftX = Math.max( rectangle1.getTopLeft().getX(), rectangle2.getTopLeft().getX() );
        return rightX - leftX;
    }
}