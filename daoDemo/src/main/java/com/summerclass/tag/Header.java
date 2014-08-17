package com.summerclass.tag;

public class Header extends StandardTag
{
    private String text;
    private String header;

    // clean up memory
    public void reset()
    {
        try
        {
            super.reset();
            text = null;
            header = null;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public String getStartHtml()
    {
        return "<h1>" + getText() + "</h1>" + getHeader();
    }

    @Override
    public String getEndHtml()
    {
        return "";
    }

    private String getHeader()
    {
        return header;
    }

    public void setHeader( String header )
    {
        this.header = header;
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
