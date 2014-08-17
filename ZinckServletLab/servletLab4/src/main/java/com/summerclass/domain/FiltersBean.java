package com.summerclass.domain;

import java.util.List;

public class FiltersBean
{
    private List<Member> members;
    private List<Employee> employees;
    private List<Club> clubs;
    private List<EventType> eventTypes;

    public FiltersBean()
    {
    }

    public FiltersBean( List<Member> members, List<Employee> employees,
                        List<Club> clubs, List<EventType> eventTypes)
    {
        setMembers( members );
        setEmployees( employees );
        setClubs( clubs );
        setEventTypes( eventTypes );
    }

    public List<Member> getMembers()
    {
        return members;
    }

    public List<Employee> getEmployees()
    {
        return employees;
    }

    public List<Club> getClubs()
    {
        return clubs;
    }

    public List<EventType> getEventTypes()
    {
        return eventTypes;
    }


    private void setMembers( List<Member> members )
    {
        this.members = members;
    }

    private void setEmployees( List<Employee> employees )
    {
        this.employees = employees;
    }

    private void setClubs( List<Club> clubs )
    {
        this.clubs = clubs;
    }

    private void setEventTypes( List<EventType> eventTypes )
    {
        this.eventTypes = eventTypes;
    }

}
