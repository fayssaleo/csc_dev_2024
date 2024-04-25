FROM openjdk:15-jdk-alpine
RUN apk add --update ttf-dejavu && rm -rf
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} csc.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/csc.jar"]