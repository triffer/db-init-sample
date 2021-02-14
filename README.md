# Database test data initialisation sample
This repository contains simple examples of how to initialize test data in a database with the Spring Boot @SQL annotation or DbUnit.

## SQL annotation
The Spring Boot @Sql annotation provides a simple way to run SQL scripts for tests.

## DbUnit
Using DbUnit we can add classes that provide a fluent API to create new database objects. In this example there is already a database.dtd, but you could also write a test component that can generate this file from an existing database.