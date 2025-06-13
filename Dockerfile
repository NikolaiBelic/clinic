FROM eclipse-temurin:11.0.26_4-jdk-focal

RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Crear directorios con permisos adecuados ANTES de copiar archivos
RUN mkdir -p /app/{config,logs,temp,filestorage,storage} \
    && chown -R 1000:1000 /app \
    && chmod -R 775 /app

COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY modules/core/src/com/company/clinic/app.properties /app/config/
COPY jmx-disable.properties /app/config/

USER 1000

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]