# syntax=docker/dockerfile:1

FROM eclipse-temurin:11

COPY mvnw pom.xml ./
COPY src/ src/

CMD ["./mvnw", "spring-boot:run"]