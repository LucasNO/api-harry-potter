FROM openjdk:11.0.8-jdk

EXPOSE 9100

ARG JAR_FILE=target/api-harry-potter-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
