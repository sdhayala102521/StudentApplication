# Use a Java runtime as base image
FROM eclipse-temurin:21-jdk
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} StudentApplication.jar
ENTRYPOINT ["java","-jar","/StudentApplication.jar"]