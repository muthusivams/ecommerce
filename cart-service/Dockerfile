FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY cart-service/ ./cart-service/
RUN set -eux; \
    success=0; \
    for i in 1 2 3 4 5; do \
      if mvn -B -c -N -f pom.xml install && mvn -B -c -f cart-service/pom.xml -DskipTests clean package; then \
        success=1; \
        break; \
      fi; \
      echo "Maven build failed (attempt ${i}/5). Cleaning local Maven cache markers and retrying in 10s..."; \
      find /root/.m2/repository -name "*.lastUpdated" -delete || true; \
      find /root/.m2/repository -name "*.part" -delete || true; \
      sleep 10; \
    done; \
    [ "$success" -eq 1 ]

FROM eclipse-temurin:8-jre
WORKDIR /app
COPY --from=build /workspace/cart-service/target/cart-service-1.0.0.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","/app/app.jar"]
