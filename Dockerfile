# Usa una imagen base con Java 17 (recomendado para Spring Boot 3+)
FROM eclipse-temurin:17-jdk-jammy

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR de tu aplicación (ajusta el nombre según tu proyecto)
COPY target/tu-app-*.jar app.jar  # Si usas Maven
# COPY build/libs/tu-app-*.jar app.jar  # Si usas Gradle

# Puerto que expone la aplicación (el mismo que usas en Spring, por defecto 8090)
EXPOSE 8090

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]