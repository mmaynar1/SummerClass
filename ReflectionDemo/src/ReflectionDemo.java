import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ReflectionDemo
{
    public final static String CLASS_NAME = ReflectionDemo.class.getName();

    private String title;
    private String caption;
    private Integer index;
    private double total;
    private boolean worked;
    private BigDecimal payment;

    public String firstName;
    public BigDecimal taxAmount;

    public ReflectionDemo()
    {
    }

    public ReflectionDemo( String title )
    {
        this.title = title;
    }

    public ReflectionDemo( String title, BigDecimal payment )
    {
        this.title = title;
        this.payment = payment;
    }

    public static void main( String[] args ) throws Exception
    {
        //demoShowMethods();

        //demoShowPublicFields();

        //demoShowAllFields( new ReflectionDemo() );

        //demoCreateByName();

        //demoConstructor( CLASS_NAME );

        //demoGetSetField( new ReflectionDemo( "initial title" ) );

        //demoGetterSetter( new ReflectionDemo( "initial title" ) );

        //demoSetFromMap();

        demoCopyFields();
    }

    private static void demoCopyFields() throws Exception
    {
        ReflectionDemo reflectionDemo = new ReflectionDemo();
        reflectionDemo.setTitle( "title" );
        reflectionDemo.setCaption( "caption" );
        reflectionDemo.setIndex( 42 );
        reflectionDemo.setPayment( new BigDecimal( "123.456" ) );

        RandomClass randomClass = new RandomClass();


        System.out.println( "before " + randomClass );
        copyFields( reflectionDemo, randomClass );
        System.out.println( "after " + randomClass );
    }

    private static void copyFields( Object source, Object destination ) throws Exception
    {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] destinationFields = destination.getClass().getDeclaredFields();

        Map<String, Field> destinationMap = new HashMap<>();
        for (Field destinationField : destinationFields)
        {
            destinationMap.put( destinationField.getName(), destinationField );
        }

        for (Field sourceField : sourceFields)
        {
            Field destinationField = destinationMap.get( sourceField.getName() );

            if ( destinationField != null &&
                 destinationField.getType().equals( sourceField.getType() ) )
            {
                destinationField.setAccessible( true );
                sourceField.setAccessible( true );

                Object value = sourceField.get( source );
                if ( value != null )
                {
                    destinationField.set( destination, value );
                }
            }
        }
    }

    private static void demoSetFromMap() throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        map.put( "title", "the title" );
        map.put( "caption", "the caption" );
        map.put( "payment", new BigDecimal( "123.45" ) );
        map.put( "index", new Integer( 5 ) );
        map.put( "notFound", new Integer( 5 ) );

        ReflectionDemo reflectionDemo = new ReflectionDemo();

        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            setValue( reflectionDemo, entry.getKey(), entry.getValue() );
        }

        System.out.println( "title = " + reflectionDemo.getTitle() + " " +
                            "caption = " + reflectionDemo.getCaption() + " + " +
                            "payment = " + reflectionDemo.getPayment() + " " +
                            "index = " + reflectionDemo.getIndex() );
    }

    private static void setValue( Object object, String name, Object value ) throws Exception
    {
        Class[] arguments = {value.getClass()};
        String methodName = "set" + name.substring( 0, 1 ).toUpperCase() + name.substring( 1 );
        try
        {
            Method method = object.getClass().getMethod( methodName, arguments );

            method.invoke( object, value );
        }
        catch (NoSuchMethodException exception)
        {
            // ignore it
        }
    }

    private static void demoGetterSetter( Object object ) throws Exception
    {
        Class theClass = object.getClass();
        Class[] arguments = {String.class};
        Method method = theClass.getMethod( "setTitle", arguments );
        method.invoke( object, "new title" );

        arguments = null;
        method = theClass.getMethod( "getTitle", arguments );
        Object value = method.invoke( object, null );
        System.out.println( "value = " + value );

        ReflectionDemo reflectionDemo = (ReflectionDemo) object;
        System.out.println( "new title = " + reflectionDemo.getTitle() );
    }

    private static void demoGetSetField( Object object ) throws Exception
    {
        Class theClass = object.getClass();
        Field field = theClass.getDeclaredField( "title" );
        field.setAccessible( true );

        Object title = field.get( object );
        System.out.println( "title = " + title );

        String newTitle = "a new title";
        field.set( object, newTitle );

        ReflectionDemo reflectionDemo = (ReflectionDemo) object;
        System.out.println( "new title = " + reflectionDemo.getTitle() );
    }

    private static void demoConstructor( String className ) throws Exception
    {
        Class theClass = Class.forName( className );
        Constructor constructor = theClass.getConstructor( String.class, BigDecimal.class );

        String title = "some title";
        BigDecimal payment = BigDecimal.ZERO;
        Object object = constructor.newInstance( title, payment );
        demoShowAllFields( object );
    }

    private static void demoShowAllFields( Object object )
    {
        Field[] fields = object.getClass().getDeclaredFields();

        System.out.println( "all fields" );
        for (Field field : fields)
        {
            System.out.println( "field = " + field.getName() );
        }
    }

    private static void demoShowPublicFields()
    {
        ReflectionDemo reflectionDemo = new ReflectionDemo();

        Field[] fields = reflectionDemo.getClass().getFields();

        for (Field field : fields)
        {
            System.out.println( "field = " + field.getName() );
        }
    }

    private static void demoShowMethods()
    {
        ReflectionDemo reflectionDemo = new ReflectionDemo();

        Method[] methods = reflectionDemo.getClass().getMethods();

        for (Method method : methods)
        {
            System.out.println( "method = " + method.getName() );
        }
    }

    private static void demoCreateByName() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {

        ReflectionDemo reflectionDemo = (ReflectionDemo) Class.forName( CLASS_NAME ).newInstance();

        reflectionDemo.setTitle( "This is the title from newInstance" );
        System.out.println( reflectionDemo.getTitle() );
    }

    public void showValues()
    {
        System.out.println( getTitle() + " " + getPayment() );
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getCaption()
    {
        return caption;
    }

    public void setCaption( String caption )
    {
        this.caption = caption;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex( Integer index )
    {
        this.index = index;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal( double total )
    {
        this.total = total;
    }

    public boolean isWorked()
    {
        return worked;
    }

    public void setWorked( boolean worked )
    {
        this.worked = worked;
    }

    public BigDecimal getPayment()
    {
        return payment;
    }

    public void setPayment( BigDecimal payment )
    {
        this.payment = payment;
    }
}
