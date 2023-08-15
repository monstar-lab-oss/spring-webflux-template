# spring-webflux-template
Provides Sample of Project for Spring Boot and Spring Webflux with maven setup. The project can serve as a template for starting a project in Spring Webflux with Java. It uses R2dbc with PostgreSQL.

## Requirements
* JDK 17
* Any IDE with [lombok](https://projectlombok.org/) plugin installed

## How to code
Import as a java project in an IDE of your choice.

Before running the app set the following env variables (take advantage of the IDEA run configuration)

`spring.r2dbc.url=`

`spring.r2dbc.schema=`

`spring.r2dbc.username=`

`spring.r2dbc.password=`

Run `mvn spring-boot:run -f pom.xml`
