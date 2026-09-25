# Spring Boot Maven Lab

A small Spring Boot project for the first Information Systems Architecture lab. It covers Maven's build lifecycle and a local MySQL connection.

## Overview

TP00 creates a Java 17 application with Maven, Spring Web, Spring Data JPA, Lombok, and the MySQL driver.

## Main Features

- Spring Boot application ready for web endpoints and JPA entities.
- MySQL settings with the password supplied through an environment variable.
- Maven lifecycle practice: clean, compile, package, and install.

## Tech Stack

Java 17 · Spring Boot 3.1.3 · Maven · Spring Web · Spring Data JPA · Hibernate · MySQL · Lombok

## Project Structure

```text
pom.xml                               Maven build and dependencies
src/main/java/tn/esprit/tpfoyer/      Spring Boot entry point
src/main/resources/                  Application settings
```

## Run Locally

Start MySQL and create `tpfoyerdb`. On this PC, MySQL uses port `3307` and the app uses `8081`. In PowerShell, set the database password, then run:

```powershell
$env:TPFOYER_DB_PASSWORD = "your-local-mysql-password"
mvn spring-boot:run
```

Set `TPFOYER_DB_URL`, `TPFOYER_DB_USER`, or `SERVER_PORT` if your setup differs. No API endpoint is defined yet, so `/` returns 404.

## Useful Commands

```bash
mvn clean
mvn compile
mvn -DskipTests package
mvn -DskipTests install
```

## Purpose

Practice creating a Spring Boot Maven project and running the main build stages before adding entities and APIs in later labs.
