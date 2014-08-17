package com.summerclass.integrationtest;

import com.summerclass.configuration.SpringConfiguration;
import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

//todo add this when ready to run servlet @WebAppConfiguration
@ContextConfiguration(classes = {SpringConfiguration.class})
public abstract class SpringIntegrationTest extends AbstractTestNGSpringContextTests
{
    protected Logger logger = Logger.getLogger( getClass() );

    @PostConstruct
    protected void initialize()
    {
    }

    @BeforeClass
    public void beforeClass()
    {
    }

    @AfterClass
    public void afterClass()
    {
    }

    @BeforeMethod
    public void beforeMethod( Method method )
    {
    }

    @AfterMethod
    public void afterMethod( ITestResult result )
    {
    }
}
