FROM maven:latest AS builder
LABEL author="boris.blagoejvicc@hotmail.com"

WORKDIR /app

COPY service/pom.xml .

RUN mvn clean install

COPY service/src ./src

RUN mvn package -Dmaven.test.skip

FROM tomcat:10

WORKDIR /usr/local/tomcat/webapps/

COPY --from=builder /app/target/messaging-app-websocket-implementation-1.0-SNAPSHOT.war  /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]