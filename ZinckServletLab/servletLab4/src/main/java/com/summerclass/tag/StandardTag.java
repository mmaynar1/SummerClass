package com.summerclass.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public abstract class StandardTag implements Tag
{
    private PageContext pageContext = null;
    private Tag parent = null;
    private int endTagReturnCode = EVAL_PAGE;
    private int startTagReturnCode = EVAL_BODY_INCLUDE;

    public abstract String getStartHtml();

    public abstract String getEndHtml();

    public PageContext getPageContext()
    {
        return pageContext;
    }

    @Override
    public void setPageContext( PageContext pageContext )
    {
        reset();
        this.pageContext = pageContext;
    }

    public void reset()
    {
        try
        {
            pageContext = null;
            parent = null;
            endTagReturnCode = EVAL_PAGE;
            startTagReturnCode = EVAL_BODY_INCLUDE;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public Tag getParent()
    {
        return parent;
    }

    @Override
    public void setParent( Tag parent )
    {
        this.parent = parent;
    }

    @Override
    public int doStartTag() throws JspException
    {
        try
        {
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.print( getStartHtml() );
        }
        catch (Exception exception)
        {
            reset();
            throw new JspException( "Fatal exception in " + getClass().getName() );
        }

        return getStartTagReturnCode();
    }

    @Override
    public int doEndTag() throws JspException
    {
        try
        {
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.print( getEndHtml() );
        }
        catch (Exception exception)
        {
            throw new JspException( "Fatal exception in " + getClass().getName() );
        }
        finally
        {
            reset();
        }

        return getEndTagReturnCode();
    }

    @Override
    public void release()
    {
        reset();
    }

    public int getEndTagReturnCode()
    {
        return endTagReturnCode;
    }

    public void setEndTagReturnCode( int endTagReturnCode )
    {
        this.endTagReturnCode = endTagReturnCode;
    }

    public int getStartTagReturnCode()
    {
        return startTagReturnCode;
    }

    public void setStartTagReturnCode( int startTagReturnCode )
    {
        this.startTagReturnCode = startTagReturnCode;
    }
}
