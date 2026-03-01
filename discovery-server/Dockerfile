FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY discovery-server/ ./discovery-server/
RUN mvn -f discovery-server/pom.xml -DskipTests clean package

FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /workspace/discovery-server/target/discovery-server-1.0.0.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/app/app.jar"]
