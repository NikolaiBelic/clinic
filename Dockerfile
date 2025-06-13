FROM eclipse-temurin:11.0.26_4-jdk-focal

# Instala dependencias y configura directorios
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && mkdir -p /app/{config,logs,temp,filestorage} \
    && chown -R 1000:1000 /app \
    && chmod -R 775 /app/{logs,temp,filestorage} \  # Más seguro que 777
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copia la aplicación y configuración
COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY etc/logback.xml /app/config/
COPY modules/core/src/com/company/clinic/app.properties /app/config/
COPY jmx-disable.properties /app/config/

USER 1000

EXPOSE 8080

ENTRYPOINT ["java", "-Dlogback.configurationFile=/app/config/logback.xml", "-jar", "app.jar"]