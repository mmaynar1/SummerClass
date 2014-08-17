package com.summerclass.tag;

import com.summerclass.domain.IdName;

import java.util.List;

public class IdNameSelect extends StandardTag
{
    List<IdName> idNames;
    String selectName;

    public void reset()
    {
        try
        {
            super.reset();
            idNames = null;
            selectName = null;
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

        html += "<select name='" + getSelectName() + "'>";

        for(IdName idName : getIdNames())
        {
            html += "<option value='" + idName.getId() + "'>" + idName.getName() + "</option>";
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

    public void setIdNames( List<IdName> idName )
    {
        this.idNames = idName;
    }

    public String getSelectName()
    {
        return selectName;
    }

    public void setSelectName( String selectName )
    {
        this.selectName = selectName;
    }
}
