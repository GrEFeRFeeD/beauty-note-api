# syntax=docker/dockerfile:1

FROM maven:3.9.2-eclipse-temurin-11-alpine as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11
COPY --from=build /target/beautynote-1.jar beautynote.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "beautynote.jar"]