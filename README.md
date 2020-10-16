# HealthCare Enrollment Microservices (BE)

A Spring Boot microservice backend application with a MySQL backend and CRUD REST API services and implementation for tracking the status of enrollees in a health care program. 

Technologies Used: Spring Cloud, JPA, Spring Boot, MySQL.

## Project Structure

### Microservices Structure
- Config (Configuration) service
    - external application-config repo for sharing properties (bootstrap.properties) across services
- Eureka Discovery Service
- Enrollee Service
- Gateway Service 

## References used and further reading: 
-[Spring Cloud – Bootstrapping](http://www.baeldung.com/spring-cloud-bootstrapping)

-[12-Factor Methodology in a Spring Boot MicroService](https://www.baeldung.com/spring-boot-12-factor)

-[Documenting a Spring REST API Using OpenAPI 3.0](https://www.baeldung.com/spring-rest-openapi-documentation)

### Running and project set up 
  
  - copy the appliction-config folder to c:\Users\{username}\ on Windows or /home/{username}/ on *nix on your machine. 
  - Then open a git bash terminal in application-config and run:
    - git init
    - git add .
    - git commit -m "First commit"

  - run the config service
  - run the discovery service
  - run all the other servers in any order (gateway, enrollee-service, etc.)