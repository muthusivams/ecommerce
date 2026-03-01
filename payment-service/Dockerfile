FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY payment-service/ ./payment-service/
RUN set -eux; \
    success=0; \
    for i in 1 2 3 4 5; do \
      if mvn -N -f pom.xml install && mvn -f payment-service/pom.xml -DskipTests clean package; then \
        success=1; \
        break; \
      fi; \
      echo "Maven build failed (attempt ${i}/5). Retrying in 10s..."; \
      sleep 10; \
    done; \
    [ "$success" -eq 1 ]

FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /workspace/payment-service/target/payment-service-1.0.0.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/app/app.jar"]
