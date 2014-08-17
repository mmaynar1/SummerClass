package com.summerclass.tag;

public class InputTag extends StandardTag
{
    private String name;
    private boolean required;
    private int maxLength;
    private String caption;

    @Override
    public void reset()
    {
        try
        {
            super.reset();
            name = null;
            caption = null;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public String getStartHtml()
    {
        String html = "<tr><td>";

        if ( isRequired() )
        {
            html += "*";
        }

        html += "</td><td><label for='" + getName() + "'>" + getCaption() + "</label></td>" +
                "<td><input type='text' maxLength='" + getMaxLength() + "' id='" + getName() + " name='" + getName() + "'></td></tr>";

        return html;
    }

    @Override
    public String getEndHtml()
    {
        return "";
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getCaption()
    {
        return caption;
    }

    public void setCaption( String caption )
    {
        this.caption = caption;
    }

    public int getMaxLength()
    {
        return maxLength;
    }

    public void setMaxLength( int maxLength )
    {
        this.maxLength = maxLength;
    }

    public boolean isRequired()
    {
        return required;
    }

    public void setRequired( boolean required )
    {
        this.required = required;
    }
}
