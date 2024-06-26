FROM maven:3.8.4-openjdk-11-slim AS builder
LABEL author="boris.blagoejvicc@hotmail.com"

WORKDIR /app

COPY service/pom.xml .

RUN mvn dependency:go-offline

COPY service/src ./src

RUN mvn clean package

FROM tomcat:9.0-jdk11-openjdk-slim

COPY --from=builder /app/target/messaging-app-websocket-implementation-1.0-SNAPSHOT.war  /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]