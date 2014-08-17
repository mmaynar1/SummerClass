package com.summerclass.domain;

public interface LoggedInMember
{
    boolean isLoggedIn();

    String getUserName();

    void setUserName( String userName );

    void clear();
}
