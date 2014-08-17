package com.summerclass.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmployeeBeanImpl implements EmployeeBean, Serializable
{
    private Employee employee;
    private List<Result> results;

    @Override
    public Employee getEmployee()
    {
        return employee;
    }

    @Override
    public void setEmployee( Employee employee )
    {
        this.employee = employee;
    }

    @Override
    public List<Result> getResults()
    {
        return results;
    }

    @Override
    public void setResults( List<Result> results )
    {
        this.results = results;
    }
}

