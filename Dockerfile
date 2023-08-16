FROM adoptopenjdk:17-jdk-hotspot

RUN apt-get update && apt-get install -y bash

WORKDIR /usr/app/

COPY target/spring-webflux-template-0.0.1-SNAPSHOT.jar /usr/app/demo.jar

CMD ["java", "-jar", "demo.jar"]
