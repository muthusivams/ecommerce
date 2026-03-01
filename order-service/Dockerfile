FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY order-service/ ./order-service/
RUN set -eux; \
    success=0; \
    for i in 1 2 3 4 5; do \
      if mvn -N -f pom.xml install && mvn -f order-service/pom.xml -DskipTests clean package; then \
        success=1; \
        break; \
      fi; \
      echo "Maven build failed (attempt ${i}/5). Retrying in 10s..."; \
      sleep 10; \
    done; \
    [ "$success" -eq 1 ]

FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /workspace/order-service/target/order-service-1.0.0.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","/app/app.jar"]
