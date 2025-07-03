# Use a Java runtime as base image
FROM java:21
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} StudentApplication.jar
ENTRYPOINT ["java","-jar","/StudentApplication.jar"]