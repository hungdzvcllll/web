FROM openjdk:26-ea-oracle
WORKDIR /app
COPY ./target/web-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "spring-0.0.1-SNAPSHOT.jar"]