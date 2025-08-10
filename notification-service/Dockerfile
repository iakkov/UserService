FROM eclipse-temurin:21-jdk-alpine
LABEL authors="iakovlysenko"
VOLUME /tmp
ARG JAR_FILE=target/notification-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]