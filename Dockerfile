# syntax=docker/dockerfile:1

FROM eclipse-temurin:11-jdk

COPY mvnw pom.xml ./
COPY src/ src/

EXPOSE 8080
CMD ["./mvnw", "build"]
CMD ["./mvnw", "spring-boot:run"]