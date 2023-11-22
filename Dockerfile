FROM alpine

RUN apk update && apk add openjdk11 && apk add maven

WORKDIR /app

COPY ./src ./src
COPY ./pom.xml ./


CMD mvn clean package && java -jar target/chatservice-1.0-SNAPSHOT-jar-with-dependencies.jar

EXPOSE 12345

