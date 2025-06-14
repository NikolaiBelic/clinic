# ---- Build Stage ----
FROM eclipse-temurin:11-jdk-focal as builder

WORKDIR /app

# Copia TODOS los archivos del proyecto (excepto lo excluido en .dockerignore)
COPY . .

# Ejecuta el build (con stacktrace para debug)
RUN chmod +x gradlew && \
    ./gradlew buildUberJar --no-daemon --build-cache --stacktrace

# ---- Runtime Stage ----
FROM eclipse-temurin:11-jdk-focal

WORKDIR /app

# Copia solo el artefacto construido (ajusta la ruta según tu proyecto)
COPY --from=builder /app/build/distributions/uberJar/clinic.jar app.jar
COPY --from=builder /app/keystore.jks .
COPY --from=builder /app/jetty.xml .

EXPOSE 8080 8443
CMD ["java", "-server", "-XX:+UseG1GC", "-jar", "app.jar"]