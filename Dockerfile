# Fase de construcción (Builder)
FROM eclipse-temurin:11-jdk-focal as builder

WORKDIR /app

# 1. Copia solo los archivos necesarios para Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 2. Ejecuta el clean y build con los parámetros requeridos
RUN chmod +x gradlew && \
    ./gradlew clean --no-daemon --build-cache && \
    ./gradlew build --no-daemon --build-cache

# Fase de ejecución
FROM eclipse-temurin:11-jdk-focal

WORKDIR /app

# 3. Copia solo el artefacto construido desde la fase builder
COPY --from=builder /app/build/distributions/uberJar/clinic.jar /app/app.jar
COPY keystore.jks /app/
COPY jetty.xml /app/

# Puerto expuesto
EXPOSE 8080 8443

# Ejecución con ajustes para producción
CMD ["java", "-server", "-XX:+UseG1GC", "-jar", "app.jar"]