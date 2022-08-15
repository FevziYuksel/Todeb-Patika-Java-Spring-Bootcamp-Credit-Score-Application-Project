# Todeb-Patika-Java-Spring-Bootcamp-Credit-Score-Application-Project
## About
This project is small adaption of credit application service made using spring boot REST API standards.

## Requirements
* Customers can create,delete or update their account.
Application must obey these critiries.
* Customers with creditscore lower than 500 is rejected.
* Creditscore higher than 500 approved.
* creditscore under 1000  and income lower than 5000 get 10000 TL limit
* under 1000 and income higher than 5000 gets 20000 TL limit
* upper 1000 get special 4 times of thir income in TL limit.
* Application can only searched by national ID.

## Technologies used

1. Java (Programming Language)
2. Spring Boot (Application Platform)
3. Spring Data JPA (Data persistence)
4. Hibernate
5. PostgreSQL
6. Swagger
7. JUnit, Mockito (Unit Testing)
8. Docker
9. JSON Web Token (Spring security)

## File Structure

The project follow monolitic layered architecture by Model ,Service, Repository, Controller approach

* Configs
    * Security
    * Swagger
* Controllers
* Exceptions
* Models
    * Entities
    * enums
    * requestDTOs
    * ResponseDTOs
    * Mappers
* Services
* Security
* Repositories


## Features

There are User , Customer and CreditApplication controller

Sign in and sign up endpoint return bearer token which required to reach other endpoints

| Type | Method |
| ------ | ------ |
| POST | localhost:8050/users/signin |

Then sign in with the following credentials: 
```
{
    "username": "admin-user",
    "password": "pass1234"
}
```
Create new customer

| Type | Method |
| ------ | ------ |
| POST | localhost:8050/v1/customer |

Request body 
```
{
    "nationalId": "22554885544",
    "firstName": "Fevzi",
    "lastName": "Yuksel",
    "monthlyIncome": 10000.0,
    "gender": "MALE",
    "age": 26,
    "phoneNo": "+905312513462",
    "email": "fevziyuksel1996@gmail.com"
}
```
Then create new application

| Type | Method |
| ------ | ------ |
| POST | localhost:8050/v1/application |

Request body 
```
{
    "nationalId": "22554885544"
}
```
Check user's applications


Then create new application

| Type | Method |
| ------ | ------ |
| GET | localhost:8050/v1/application/all/{22554885544} |



Other endpoints can be reached from this link

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/8288169938a24bbadcec?action=collection%2Fimport)


## Docker
First you need to uncomment this line in application.properties;

* spring.profiles.active=docker

Start Docker Engine in your machine.

Run mvn clean install.

Build executable jar file - mvn clean package

Run application on docker 

docker compose up


## API Documentation

API documentation can be accessed via [Swagger UI](localhost:8050/swagger-ui/index.html) 



 <p align="center"><img src="./assets/img/Database Design Architecture.png" alt="Project Branding Image"></a></p>