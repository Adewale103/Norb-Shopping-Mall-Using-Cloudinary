FROM openjdk:8-jdk-alpine
MAINTAINER norbs-project
EXPOSE 8080
ADD target/norbs-project.jar norbs-project.jar
ENTRYPOINT ["java", "-jar", "/norbs-project.jar"]