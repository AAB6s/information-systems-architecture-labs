# AutoLoc API

A Spring Boot project for the AutoLoc vehicle rental workshops. TP03 adds the Spring Data JPA repository layer.

## Overview

Each entity has an interface in `tn.esprit.autoloc.repository`. Spring Data provides the implementations when the application starts.

## Main Features

- Nine repositories extending `JpaRepository<Entity, Long>`.
- CRUD, lists, sorting, pagination, and flush through Spring Data JPA.
- Repository choices and code quality findings in `docs/repository-notes.md`.

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
  repository/      Spring Data JPA interfaces
  AutolocApiApplication.java
src/main/resources/application.properties
docs/repository-notes.md
```

## Run Locally

Set `AUTOLOC_DB_PASSWORD` to your MySQL root password, then run:

```bash
mvn spring-boot:run
```

MySQL uses `localhost:3307` and the `autoloc_db` database on this PC.

## Useful Commands

```bash
mvn clean verify
mvn spring-boot:run
```

## Purpose

TP03 provides database access through Spring Data JPA and verifies that the application detects all nine repositories.
