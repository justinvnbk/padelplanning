FROM maven:3.9.11-amazoncorretto-24 as build
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests
FROM amazoncorretto:24-alpine-jdk
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]