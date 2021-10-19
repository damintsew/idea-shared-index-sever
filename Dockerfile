FROM openjdk:17-jdk-alpine
MAINTAINER damintsew.ru

RUN mkdir -p /var/data

COPY web-server/target/web-server*.jar web-server.jar
ENTRYPOINT ["java","-jar","/web-server.jar"]