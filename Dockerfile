FROM eclipse-temurin:11.0.26_4-jdk-focal

# Instala dependencias y configura directorios
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && mkdir -p /tmp/logs /storage \
    && chown -R 1000:1000 /tmp/logs /storage \
    && chmod -R 775 /tmp /tmp/logs /storage \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copia la aplicación
COPY build/distributions/uberJar/clinic.jar /app/app.jar

USER 1000

EXPOSE 8080

ENTRYPOINT ["java", \
    "-Djava.io.tmpdir=/tmp", \
    "-Dcuba.tempDir=/tmp", \
    "-Dcuba.logDir=/tmp/logs", \
    "-Dcuba.fileStorageDir=/storage", \
    "-Dspring.jmx.enabled=false", \
    "-jar", "app.jar"]