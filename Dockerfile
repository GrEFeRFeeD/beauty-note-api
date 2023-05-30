# syntax=docker/dockerfile:1

FROM eclipse-temurin:11-jdk

COPY mvnw pom.xml ./
COPY src/ src/
COPY ./.mvn ./.mvn

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]