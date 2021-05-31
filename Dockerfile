FROM openjdk:8-jdk-alpine
MAINTAINER bondak
COPY catdog-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/catdog-0.0.1-SNAPSHOT.jar"]

