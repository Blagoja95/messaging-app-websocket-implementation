FROM maven:3.8.4-openjdk-11-slim AS builder
LABEL author="boris.blagoejvicc@hotmail.com"

WORKDIR /app

COPY service/pom.xml .

RUN mvn clean install

COPY service/src ./src

RUN mvn package

FROM tomcat:8.5.47-jdk8-openjdk

COPY --from=builder /app/target/messaging-app-websocket-implementation-1.0-SNAPSHOT.war  /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]