FROM openjdk:26-ea-oracle
WORKDIR /app
COPY ./target/web-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]