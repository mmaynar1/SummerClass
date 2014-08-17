package com.summerclass.tag;

import com.summerclass.domain.Club;

import java.util.List;

public class OrderedList extends StandardTag
{
    private List<Club> clubs;
    private String caption;

    // clean up memory
    public void reset()
    {
        try
        {
            super.reset();
            clubs = null;
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
        StringBuilder html = new StringBuilder();

        html.append( "<h3>" );
        html.append( getCaption() );
        html.append( "</h3>" );
        html.append( "<ul>" );
        for (Club club : getClubs())
        {
            html.append( "<li>" );
            html.append( club.getNumber() );
            html.append( " " );
            html.append( club.getName() );
            html.append( "</li>" );
        }
        html.append( "</ul>" );

        return html.toString();
    }

    @Override
    public String getEndHtml()
    {
        return "";
    }

    public List<Club> getClubs()
    {
        return clubs;
    }

    public void setClubs( List<Club> clubs )
    {
        this.clubs = clubs;
    }

    public String getCaption()
    {
        return caption;
    }

    public void setCaption( String caption )
    {
        this.caption = caption;
    }
}
