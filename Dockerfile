FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/registro_pagos-*.jar app.jar

EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
