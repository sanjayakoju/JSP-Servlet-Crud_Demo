CREATE DATABASE jsp-servlet-mysql-demo;
USE jsp-servlet-mysql-demo;

create table users (
	id  int(3) NOT NULL AUTO_INCREMENT,
	name varchar(120) NOT NULL,
	email varchar(220) NOT NULL,
	country varchar(120),
	PRIMARY KEY (id)
);