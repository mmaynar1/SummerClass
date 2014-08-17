package com.summerclass.domain;

import com.summerclass.utility.StringSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoggedInMemberImpl implements LoggedInMember
{
    private String userName;

    @Override
    public boolean isLoggedIn()
    {
        return !StringSupport.isEmptyString( getUserName() );
    }

    @Override
    public String getUserName()
    {
        return userName;
    }

    @Override
    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    @Override
    public void clear()
    {
        setUserName( null );
    }
}
