FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY payment-service/ ./payment-service/
RUN mvn -N -f pom.xml install && mvn -f payment-service/pom.xml -DskipTests clean package

FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /workspace/payment-service/target/payment-service-1.0.0.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/app/app.jar"]
