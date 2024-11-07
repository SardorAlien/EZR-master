FROM openjdk:11-jdk

WORKDIR /app

## just copying the application from target folder into a container
COPY /target/ezr.jar /app/ezr.jar

EXPOSE 8081

CMD ["java","-jar", "ezr.jar"]
