package com.summerclass.tag;

public class Out extends StandardTag
{
    private String value;

    // clean up memory
    public void reset()
    {
        try
        {
            super.reset();
            value = null;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public String getStartHtml()
    {
        return getValue();
    }

    @Override
    public String getEndHtml()
    {
        return "";
    }

    private String getValue()
    {
        return value;
    }

    public void setValue( String text )
    {
        this.value = text;
    }
}
