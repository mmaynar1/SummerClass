use abcdb;

drop function if exists getGuid;
DELIMITER //
create function getGuid() returns char(32) DETERMINISTIC
begin
      declare guid char(32);
      select replace( uuid(), '-', '' ) into guid;
      return guid;
end; //
DELIMITER ;

drop table if exists eventSessions;
drop table if exists statuses;
drop table if exists members;
drop table if exists clubs;
drop table if exists eventTypes;
drop table if exists employees;

/* Members */
create table members
(
m_id char(32) not null,
m_first_name varchar(50) not null,
m_last_name varchar(50) not null,
primary key( m_id )
);

insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Andy', 'McNeely');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Bob', 'Barker');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Charles', 'Bronson');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Dan', 'Marino');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Ellen', 'Degeneres');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Farah', 'Fawcett');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Gloria', 'Esteban');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Heather', 'Graham');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Isis', 'Notarealname');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Jackie', 'Robinson');
insert into members (m_id, m_first_name, m_last_name) 
values (getGuid(), 'Mitchum', 'Maynard');

select * from members;

/* Clubs */


create table clubs
(
c_id char(32) not null,
c_number int not null unique,
c_name varchar(50) not null unique,
primary key( c_id )
);

insert into clubs (c_id, c_number, c_name) 
values (getGuid(), 3000, 'Sherwood');
insert into clubs (c_id, c_number, c_name) 
values (getGuid(), 3001, 'Conway');
insert into clubs (c_id, c_number, c_name) 
values (getGuid(), 3002, 'Searcy');

select * from clubs;

/* Event Types */



create table eventTypes
(
et_id char(32) not null,
et_name varchar(50) not null unique,
primary key( et_id )
);

insert into eventTypes (et_id, et_name) 
values (getGuid(), 'Personal Training');
insert into eventTypes (et_id, et_name) 
values (getGuid(), 'Zumba');
insert into eventTypes (et_id, et_name) 
values (getGuid(), 'Pilates');
insert into eventTypes (et_id, et_name) 
values (getGuid(), 'Kick Boxing');
insert into eventTypes (et_id, et_name) 
values (getGuid(), 'Test Event');

select * from eventTypes;

/* Employees */
create table employees
(
e_id char(32) not null,
e_first_name varchar(50) not null,
e_last_name varchar(50) not null,
primary key( e_id )
);


insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Kenneth', 'Maynard');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Leon', 'Theprofessional');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Marty', 'McFly');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Nathan', 'Lockwood');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Opie', 'Griffith');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Pam', 'Beasley');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Ray', 'Orbison');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Samuel', 'L. Jackson');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Todd', 'Jones');
insert into employees (e_id, e_first_name, e_last_name) 
values (getGuid(), 'Venus', 'Flytrap');


select * from employees;


/* Statuses */
create table statuses
(
s_id char(32) not null,
s_abc_code varchar(10) not null unique,
s_name varchar(50) not null unique,
primary key( s_id )
);

insert into statuses (s_id, s_abc_code, s_name) 
values (getGuid(), 'PEN', 'Pending');
insert into statuses (s_id, s_abc_code, s_name) 
values (getGuid(), 'COM', 'Complete');
insert into statuses (s_id, s_abc_code, s_name) 
values (getGuid(), 'CAN', 'Cancelled');

select * from statuses;

/* eventSession */


create table eventSessions
(
es_id char(32) not null,
es_start timestamp not null,
m_id char(32) not null,
e_id char(32) not null,
s_id char(32) not null ,
et_id char(32) not null ,
c_id char(32) not null ,
primary key( es_id ),
constraint fk_es_m foreign key (m_id) references members (m_id),
constraint fk_es_e foreign key (e_id) references employees (e_id),
constraint fk_es_s foreign key (s_id) references statuses (s_id),
constraint fk_es_et foreign key (et_id) references eventTypes (et_id),
constraint fk_es_c foreign key (c_id) references clubs (c_id)
);


insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start)
values( getGuid(),
(select m_id from members where m_first_name = 'Andy'),
(select e_id from employees where e_first_name = 'Marty'),
(select s_id from statuses where s_abc_code = 'PEN'),
(select c_id from clubs where c_number = 3001),
(select et_id from eventtypes where et_name = 'Zumba'),
now() );

insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start)
values( getGuid(),
(select m_id from members where m_first_name = 'Mitchum'),
(select e_id from employees where e_first_name = 'Pam'),
(select s_id from statuses where s_abc_code = 'PEN'),
(select c_id from clubs where c_number = 3000),
(select et_id from eventtypes where et_name = 'Zumba'),
now() );

insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start)
values( getGuid(),
(select m_id from members where m_first_name = 'Dan'),
(select e_id from employees where e_first_name = 'Leon'),
(select s_id from statuses where s_abc_code = 'PEN'),
(select c_id from clubs where c_number = 3002),
(select et_id from eventtypes where et_name = 'Kick Boxing'),
now() );

insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start)
values( getGuid(),
(select m_id from members where m_first_name = 'Gloria'),
(select e_id from employees where e_first_name = 'Pam'),
(select s_id from statuses where s_abc_code = 'PEN'),
(select c_id from clubs where c_number = 3000),
(select et_id from eventtypes where et_name = 'Zumba'),
now() );

select * from eventSessions;

select es.es_id, c.c_name, es.es_start, m.m_first_name, 
       e.e_first_name, et.et_name, s.s_name
from eventsessions es
join members m on m.m_id = es.m_id
join employees e on e.e_id = es.e_id
join eventtypes et on et.et_id = es.et_id
join clubs c on c.c_id = es.c_id
join statuses s on s.s_id = es.s_id
order by es.es_start;


