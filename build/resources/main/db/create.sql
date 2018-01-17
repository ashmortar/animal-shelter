SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS animals (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 gender VARCHAR,
 admittance DATE,
 type VARCHAR,
 breed VARCHAR,
 ownerid INTEGER,
 adopted BOOLEAN
 );

 CREATE TABLE IF NOT EXISTS persons (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 phone VARCHAR,
 typePreference VARCHAR,
 breedPreference VARCHAR
 );