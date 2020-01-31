# **Developer CRUD application**
[![Build Status](https://api.travis-ci.com/TergBug/hw7.svg?branch=master)](https://travis-ci.com/TergBug/hw7)

It is a RESTful CRUD application that has next entities:
Developer,
Skill,
Account.

Developer:
Long id;
String firstName;
String lastName;
Set<Skill> skills;
Account account.

Skill:
Long id;
String name.

Account:
Long id;
String name;
AccountStatus (enum: ACTIVE, BANNED, DELETED) status.

It uses MySQL database as a storage.

User is able to create, read (get by ID or get all), update and delete data.

Layers:
>model - POJO classes

>rest - servlets that handles user’s POST requests from network via HTTP/HTTPS protocol

>service - simple business logic

>repository - classes that provide access to storage

>storage - tables in database

Class-chain for developer (not inheritance):
DeveloperRepository -> DeveloperService -> DeveloperServlet

Class-chain for skill (not inheritance):
SkillRepository -> SkillService -> SkillServlet

Class-chain for account (not inheritance):
AccountRepository -> AccountService -> AccountServlet

For repository layer there are a few interfaces, such as 
GenericRepository<T,ID>, DeveloperRepository, SkillRepository, AccountRepository.
DeveloperRepository, SkillRepository, AccountRepository extend GenericRepository<T,ID>.
Classes JDBCDeveloperRepositoryImpl, JDBCSkillRepositoryImpl, JDBCAccountRepositoryImpl 
implement appropriate interfaces.

All basic functionality is covered with unit tests, using JUnit and Mockito. Also pocket 
H2 DB is used to implement tests with connection to database. 

Liquibase is used to initialize tables in DB and fill them by some information automatically 
during test (for H2 DB) and deploy (for remote DB) phases.

There are a few endpoints in this API, each of them associate with certain servlets:
>/api/v1/skills

>/api/v1/accounts

>/api/vi/developers

There is one index.jsp page, which contains description of project in three languages: 
English, Russian and Chinese. And there is a documentation page on endpoint /documentation, 
which has been built with Swagger UI.

To start up application you should compile code (version of Java is 8) and start this with 
entry point in Main class. Then the tomcat server will start locally.

This api is deployed to heroku cloud service. Link to project: https://developercrud.herokuapp.com.