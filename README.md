# AutoLoc API

A Spring Boot project for the AutoLoc vehicle rental workshop. This branch covers the first JPA entity and the eight additional entities from TP01.

## Overview

The project maps the rental domain to MySQL. Associations and API endpoints belong to later workshops.

## Main Features

- Nine JPA entities with generated IDs.
- String based enums for vehicle category, status, employee role, reservation status, and payment mode.
- Hibernate schema creation in MySQL.

## Tech Stack

- Java 17
- Spring Boot 3.1.3
- Maven
- Spring Data JPA and MySQL
- Lombok

## Project Structure

```text
src/main/java/tn/esprit/autoloc/
  domain/          Entities and enums
  AutolocApiApplication.java
src/main/resources/application.properties
```

## Run Locally

Set `AUTOLOC_DB_PASSWORD` to your MySQL root password, then run:

```bash
mvn spring-boot:run
```

MySQL uses `localhost:3307` and the `autoloc_db` database in this setup.

## Useful Commands

```bash
mvn clean verify
mvn spring-boot:run
```

## Purpose

TP01 establishes the AutoLoc data model and verifies that all nine tables appear in MySQL.
