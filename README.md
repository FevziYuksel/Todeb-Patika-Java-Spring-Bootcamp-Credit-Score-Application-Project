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


## API Documentation

API documentation can be accessed via ![Swagger UI](localhost:8050/swagger-ui/index.html)

## Getting Started

The source code can be checked out to your local and then build and run the application either from your IDE after importing to it as a maven project, or just from a command line. Follow these steps for the command-line option:  

### Prerequisites
1. Java 8
2. Maven 3
3. Git
4. PostgreSQL


### Installing & Running

#### Clone this repo into your local: 
	
```
git clone https://github.com/FevziYuksel/Todeb-Patika-Java-Spring-Bootcamp-Credit-Score-Application-Project.git
```

####  Build using maven 
	
```
mvn clean install
```
	
#### Start the app
	
```
mvn spring-boot:run
```
	
#### Access the Home screen

The application will be available at the URL: [Home](http://localhost:8050).

## Docker
First you need to uncomment this line in application.properties;

* spring.profiles.active=docker

Start Docker Engine in your machine.

Run mvn clean install.

Build executable jar file - mvn clean package

Run application on docker 

docker compose up


## Kafka
Exposing the Costumer as a service to be used by another micro-service. [Kafka Consumer Project](https://github.com/FevziYuksel/Todeb-Credit-Kafka-Consumer).
When Credit Application approved, credit will produce and send to consumer

Start Kafka-server and ZooKeeper after installing it on your machine

### It doesn't working yet When receive the message throw Consumer exception. When receive the message throw Consumer exception. 

## Running the Test Cases

Test cases for this project:

[CustomerControllerTest.java](src/test/java/com/todebpatikajavaspringbootcampcreditscoreapplicationproject/controller/CustomerControllerTest.java),

[CreditApplicationControllerTest.java](src/test/java/com/todebpatikajavaspringbootcampcreditscoreapplicationproject/controller/CreditApplicationControllerTest.java)

[CustomerServiceTest.java](src/test/java/com/todebpatikajavaspringbootcampcreditscoreapplicationproject/service/impl/CustomerServiceTest.java)

[CreditApplicationTest.java](src/test/java/com/todebpatikajavaspringbootcampcreditscoreapplicationproject/service/impl/CreditApplicationServiceTest.java)

[CreditServiceTest](src/test/java/com/todebpatikajavaspringbootcampcreditscoreapplicationproject/service/impl/CreditServiceTest.java)

[NotificationServiceTest](src/test/java/com/todebpatikajavaspringbootcampcreditscoreapplicationproject/service/impl/NotificationServiceTest.java)

You can run it either from:

- Command line

```
mvn test
```

- Your IDE


	Right click on this file and "Run As JUnit Testcase"  




 ## Database Design Architecture
![Diagram](https://github.com/FevziYuksel/Todeb-Patika-Java-Spring-Bootcamp-Credit-Score-Application-Project/blob/master/Database%20Design%20Architecture.png))

