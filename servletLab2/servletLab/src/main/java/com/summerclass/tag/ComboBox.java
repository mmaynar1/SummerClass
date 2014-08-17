package com.summerclass.tag;

import com.summerclass.domain.Club;
import com.summerclass.domain.IdName;

import java.util.List;

public class ComboBox extends StandardTag
{
    private List<IdName> idNames;
    private String selectIdName;
    private String selectedId;

    // clean up memory
    public void reset()
    {
        try
        {
            super.reset();
            for (IdName idName : idNames)
            {
                idName = null;
            }
            idNames = null;
            selectIdName = null;
            selectedId = null;

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public String getStartHtml()
    {
        String html = "";

        html += "<select id='" + getSelectIdName() + "' name='" + getSelectIdName() + "'>";

        for (IdName idName : getIdNames())
        {

            if ( getSelectedId() != null && getSelectedId().equals( idName.getId() ) )
            {
                html += "<option selected='selected' value='" + idName.getId() + "'>" + idName.getName() + "</option>";
            }
            else
            {
                html += "<option value='" + idName.getId() + "'>" + idName.getName() + "</option>";
            }

        }

        html += "</select>";

        return html;
    }

    @Override
    public String getEndHtml()
    {
        return "";
    }


    public List<IdName> getIdNames()
    {
        return idNames;
    }

    public void setIdNames( List<IdName> idNames )
    {
        this.idNames = idNames;
    }

    public String getSelectIdName()
    {
        return selectIdName;
    }

    public void setSelectIdName( String selectIdName )
    {
        this.selectIdName = selectIdName;
    }

    public String getSelectedId()
    {
        return selectedId;
    }

    public void setSelectedId( String selectedId )
    {
        this.selectedId = selectedId;
    }
}
