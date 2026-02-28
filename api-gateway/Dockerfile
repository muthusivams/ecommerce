FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /app
COPY . .
RUN mvn -DskipTests clean package
FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /app/target/api-gateway-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
