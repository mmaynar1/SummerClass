package com.summerclass.domain;

import com.summerclass.utility.StringSupport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee
{
    private String employeeId;
    private String firstName;
    private String lastName;
    private String barcode;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String hourlyWage;
    private Timestamp startDate;


    @Override
    public String toString()
    {
        return "Member{" +
               "memberId='" + getEmployeeId() + '\'' +
               ", firstName='" + getFirstName() + '\'' +
               ", lastName='" + getLastName() + '\'' +
               '}';
    }

    public Employee()
    {
        setFirstName( "" );
        setLastName( "" );
        setAddress( "" );
        setCity( "" );
        setState( "" );
        setBarcode( "" );
        setZipCode( "" );
        setEmployeeId( "" );
        setHourlyWage( "" );
    }

    public static class Mapper implements RowMapper<Employee>
    {
        public final static String QUERY = "select e.e_id, e.e_first_name, e.e_last_name, e.e_barcode, e.e_address, e.e_city, e.e_state, e.e_zip_code, e.e_hourly_wage, e.e_start_date " +
                                           "from employees e ";

        public Employee mapRow( ResultSet resultSet, int rowNumber ) throws SQLException
        {
            Employee employee = new Employee();

            employee.setEmployeeId( resultSet.getString( "E_ID" ) );
            employee.setFirstName( resultSet.getString( "E_FIRST_NAME" ) );
            employee.setLastName( resultSet.getString( "E_LAST_NAME" ) );
            employee.setBarcode( resultSet.getString( "E_BARCODE" ) );
            employee.setAddress( resultSet.getString( "E_ADDRESS" ) );
            employee.setCity( resultSet.getString( "E_CITY" ) );
            employee.setState( resultSet.getString( "E_STATE" ) );
            employee.setZipCode( resultSet.getString( "E_ZIP_CODE" ) );
            employee.setHourlyWage( resultSet.getString( "E_HOURLY_WAGE" ) );
            employee.setStartDate( resultSet.getTimestamp( "E_START_DATE" ) );
            return employee;
        }
    }

    public List<Result> validateEditableFields()
    {
        List<Result> results = new ArrayList<>();

        if ( StringSupport.isEmptyString( getFirstName() ) )
        {
            results.add( new Result( Result.Status.failure, "First Name is a required field" ) );
        }
        if ( StringSupport.isEmptyString( getLastName() ) )
        {
            results.add( new Result( Result.Status.failure, "Last Name is a required field" ) );
        }
        if ( StringSupport.isEmptyString( getBarcode() ) )
        {
            results.add( new Result( Result.Status.failure, "Barcode is a required field" ) );
        }
        if ( StringSupport.isEmptyString( getFirstName() ) )
        {
            results.add( new Result( Result.Status.failure, "First Name is a required field" ) );
        }
        if ( StringSupport.isEmptyString( getAddress() ) )
        {
            results.add( new Result( Result.Status.failure, "Address is a required field" ) );
        }
        if ( StringSupport.isEmptyString( getCity() ) )
        {
            results.add( new Result( Result.Status.failure, "City is a required field" ) );
        }
        if ( StringSupport.isEmptyString( getState() ) )
        {
            results.add( new Result( Result.Status.failure, "State is a required field" ) );
        }
        if ( StringSupport.isEmptyString( getZipCode() ) )
        {
            results.add( new Result( Result.Status.failure, "Zip Code is a required field" ) );
        }
        if ( StringSupport.isEmptyString( getHourlyWage() ) )
        {
            results.add( new Result( Result.Status.failure, "Hourly Wage is a required field" ) );
        }

        results.add( validateField(Pattern.compile( "[0-9]{5}" ), getZipCode(), "zip code") );
        results.add( validateField( Pattern.compile( "[0-9]+" ), getHourlyWage(), "hourly wage" ) );
        results.add( validateField( Pattern.compile( "^((A[LKSZR])|(C[AOT])|(D[EC])|(F[ML])|(G[AU])|(HI)|(I[DLNA])|(K[SY])|(LA)|(M[EHDAINSOT])|(N[EVHJMYCD])|(MP)|(O[HKR])|(P[WAR])|(RI)|(S[CD])|(T[NX])|(UT)|(V[TIA])|(W[AVIY]))$"), getState(), " state" ) ) ;

        return results;
    }

    private Result validateField( Pattern pattern, String field, String message )
    {
        Matcher matcher = pattern.matcher( field );
        boolean matches = matcher.matches();
        Result result;
        if ( !matches )
        {
            result = new Result( Result.Status.failure, "Please enter a valid " + message  );
        }
        else
        {
            result = new Result( Result.Status.success, "" );
        }

        return result;
    }


    public String getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId( String employeeId )
    {
        this.employeeId = employeeId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode( String barcode )
    {
        this.barcode = barcode;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState( String state )
    {
        this.state = state.toUpperCase();
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode( String zipCode )
    {
        this.zipCode = zipCode;
    }

    public String getHourlyWage()
    {
        return hourlyWage;
    }

    public void setHourlyWage( String hourlyWage )
    {
        this.hourlyWage = hourlyWage;
    }

    public Timestamp getStartDate()
    {
        return startDate;
    }

    public void setStartDate( Timestamp startDate )
    {
        this.startDate = startDate;
    }
}
