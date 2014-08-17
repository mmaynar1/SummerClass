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
drop table if exists employees;
drop table if exists members;
drop table if exists agreementTypes;
drop table if exists clubs;
drop table if exists statuses;
drop table if exists eventTypes;

create table employees
(
  e_id char(32) not null,
  e_first_name varchar(50) not null,
  e_last_name varchar(50) not null,
  primary key( e_id )
);

insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Andy', 'Smith' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Bob', 'Jones' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Charles', 'Wood' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Dan', 'Small' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Ellen', 'Oak' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Farah', 'Waters' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Gloria', 'Johnson' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Heather', 'Mall' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Isis', 'Eathan' );
insert into employees  (e_id, e_first_name, e_last_name ) values( getGuid(), 'Jackiet', 'Kennedy' );

create table agreementTypes
(
  at_id char(32) not null,
  at_name varchar(50) not null,
  at_abc_code varchar(50) not null,
  primary key( at_id ),
  unique key ( at_name ),
  unique key ( at_abc_code )
);

insert into agreementTypes (at_id, at_name, at_abc_code) values( getGuid(), 'Gold', 'GOLD' );
insert into agreementTypes (at_id, at_name, at_abc_code) values( getGuid(), 'Silver', 'SILVER' );
insert into agreementTypes (at_id, at_name, at_abc_code) values( getGuid(), 'Bronze', 'BRONZE' );

create table members
(
  m_id char(32) not null,
  m_first_name varchar(50) not null,
  m_last_name varchar(50) not null,
  m_username varchar(25) unique not null,
  m_password varchar(25) not null,
  at_id char(32) not null,
  primary key( m_id ),
  CONSTRAINT fk_m_at FOREIGN KEY (at_id) REFERENCES agreementTypes (at_id)
);

insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Kenneth', 'Teeter', 'Kenneth', 'password', (select at_id from agreementTypes where at_abc_code = 'GOLD') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Leon', 'Brunett', 'Leon', 'password', (select at_id from agreementTypes where at_abc_code = 'SILVER') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Marty', 'Canton', 'Marty', 'password', (select at_id from agreementTypes where at_abc_code = 'GOLD') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Nathan', 'Garrett', 'Nathan', 'password', (select at_id from agreementTypes where at_abc_code = 'BRONZE') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Opie', 'McFarlan', 'Opie', 'password', (select at_id from agreementTypes where at_abc_code = 'GOLD') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Pam', 'Ryan', 'Pam', 'password', (select at_id from agreementTypes where at_abc_code = 'SILVER') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Ray', 'Reaves', 'Ray', 'password', (select at_id from agreementTypes where at_abc_code = 'GOLD') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Samuel', 'Harris', 'Samuel', 'password', (select at_id from agreementTypes where at_abc_code = 'BRONZE') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Todd', 'Albright', 'Todd', 'password', (select at_id from agreementTypes where at_abc_code = 'GOLD') );
insert into members (m_id, m_first_name, m_last_name, m_username, m_password, at_id ) values( getGuid(), 'Venus', 'McQray', 'Venus', 'password', (select at_id from agreementTypes where at_abc_code = 'GOLD') );

create table clubs
(
  c_id char(32) not null,
  c_number int not null,
  c_name varchar(50) not null,
  primary key( c_id ),
  unique key ( c_number ),
  unique key ( c_name )
);

insert into clubs  (c_id, c_name, c_number ) values( getGuid(), 'Sherwood', 3000 );
insert into clubs  (c_id, c_name, c_number ) values( getGuid(), 'Conway', 3001 );
insert into clubs  (c_id, c_name, c_number ) values( getGuid(), 'Briant', 3002 );

create table eventTypes
(
  et_id char(32) not null,
  et_name varchar(50) not null,
  primary key( et_id ),
  unique key ( et_name )
);

insert into eventTypes (et_id, et_name) values( getGuid(), 'Personal Training' );
insert into eventTypes (et_id, et_name) values( getGuid(), 'Zumba' );
insert into eventTypes (et_id, et_name) values( getGuid(), 'Pilates' );
insert into eventTypes (et_id, et_name) values( getGuid(), 'Kick Boxing' );

create table statuses
(
  s_id char(32) not null,
  s_name varchar(50) not null,
  s_abc_code varchar(25),
  primary key( s_id ),
  unique key ( s_abc_code ),
  unique key ( s_name )
);

insert into statuses (s_id, s_name, s_abc_code ) values( getGuid(), 'Pending', 'PEN' );
insert into statuses (s_id, s_name, s_abc_code ) values( getGuid(), 'Complete', 'COM' );
insert into statuses (s_id, s_name, s_abc_code ) values( getGuid(), 'Cancelled', 'CAN' );

create table eventSessions
(
  es_id char(32) not null,
  es_start timestamp not null,
  m_id char(32) not null,
  e_id char(32) not null,
  s_id char(32) not null,
  c_id char(32) not null,
  et_id char(32) not null,

  primary key( es_id ),
  CONSTRAINT fk_es_m FOREIGN KEY (m_id) REFERENCES members (m_id),
  CONSTRAINT fk_es_et FOREIGN KEY (et_id) REFERENCES eventTypes (et_id),
  CONSTRAINT fk_es_s FOREIGN KEY (s_id) REFERENCES statuses (s_id),
  CONSTRAINT fk_es_c FOREIGN KEY (c_id) REFERENCES clubs (c_id),
  CONSTRAINT fk_es_e FOREIGN KEY (e_id) REFERENCES employees (e_id)
);

insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start)
values( getGuid(),
        (select m_id from members where m_first_name = 'Leon'),
        (select e_id from employees where e_first_name = 'Bob'),
        (select s_id from statuses where s_abc_code = 'PEN'),
        (select c_id from clubs where c_number = 3000),
        (select et_id from eventtypes where et_name = 'Zumba'),
	      now() );

insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start)
values( getGuid(),
        (select m_id from members where m_first_name = 'Marty'),
        (select e_id from employees where e_first_name = 'Charles'),
        (select s_id from statuses where s_abc_code = 'CAN'),
        (select c_id from clubs where c_number = 3001),
        (select et_id from eventtypes where et_name = 'Kick Boxing'),
	      now() );

insert into eventSessions (es_id, m_id, e_id, s_id, c_id, et_id, es_start)
values( getGuid(),
        (select m_id from members where m_first_name = 'Nathan'),
        (select e_id from employees where e_first_name = 'Dan'),
        (select s_id from statuses where s_abc_code = 'COM'),
        (select c_id from clubs where c_number = 3002),
        (select et_id from eventtypes where et_name = 'Personal Training'),
	      now() );

select es.es_id, c.c_name, es.es_start, at.at_name, m.m_first_name, e.e_first_name, et.et_name, s.s_name
from eventSessions es
join members m on m.m_id = es.m_id
join employees e on e.e_id = es.e_id
join eventTypes et on et.et_id = es.et_id
join clubs c on c.c_id = es.c_id
join statuses s on s.s_id = es.s_id
join agreementTypes at on at.at_id = m.at_id
order by es.es_start;