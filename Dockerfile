FROM eclipse-temurin:11.0.26_4-jdk-focal

# Instala dependencias y configura directorios
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && mkdir -p /app/{config,logs,temp,filestorage,storage} \
    && chmod -R 777 /app \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copia la aplicación y configuración
COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY etc/logback.xml /app/config/
COPY modules/core/src/com/company/clinic/app.properties /app/config/
COPY jmx-disable.properties /app/config/

# Asegura permisos correctos
RUN chown -R 1000:1000 /app

USER 1000

EXPOSE 8080

# Usa tu logback.xml personalizado
ENTRYPOINT ["java", "-Dlogback.configurationFile=/app/config/logback.xml", "-jar", "app.jar"]