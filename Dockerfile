FROM maven:3.6-openjdk-17 AS build
COPY . ./
RUN mvn clean install package

FROM openjdk:17 AS run
COPY . ./
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/recrutation-0.0.1-SNAPSHOT.jar"]