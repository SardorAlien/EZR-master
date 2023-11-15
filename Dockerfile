FROM openjdk:11
EXPOSE 8081
ADD target/ezr.jar ezr.jar
ENTRYPOINT ["java", "-jar", "/ezr.jar"]