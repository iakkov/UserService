FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY user-service-db ./user-service-db
COPY user-service-domain ./user-service-domain
COPY user-service-impl ./user-service-impl
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
LABEL authors="iakovlysenko"
VOLUME /tmp
COPY --from=build /app/user-service-impl/target/user-service-impl-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


