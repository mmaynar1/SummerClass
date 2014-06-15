import java.awt.*;

public class Rectangle
{
    private Point topLeft;
    private Point bottomRight;

    Rectangle(Point topLeft, Point bottomRight)
    {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft()
    {
        return topLeft;
    }

    public Point getBottomRight()
    {
        return bottomRight;
    }

}
