# ---- Etapa de construcción (Build) ----
FROM eclipse-temurin:11-jdk-focal as builder

WORKDIR /app

# 1. Copia los archivos necesarios para Gradle (para mejor caché)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 2. Ejecuta el build (genera el uberJar)
RUN chmod +x gradlew && \
    ./gradlew buildUberJar --no-daemon --build-cache

# ---- Etapa de ejecución (Runtime) ----
FROM eclipse-temurin:11-jdk-focal

WORKDIR /app

# 3. Copia SOLO el JAR generado desde la etapa builder
COPY --from=builder /app/build/distributions/uberJar/clinic.jar app.jar
COPY keystore.jks .
COPY jetty.xml .

# 4. Expone puertos y define el comando de arranque
EXPOSE 8080 8443
CMD ["java", "-server", "-XX:+UseG1GC", "-jar", "app.jar"]