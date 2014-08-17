package com.summerclass.tag;

public class Output extends StandardTag
{
    String text;
    public void reset()
    {
        try
        {
            super.reset();
            text = null;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public String getStartHtml()
    {
        return getText();
    }

    @Override
    public String getEndHtml()
    {
        return "";
    }

    private String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }
}
