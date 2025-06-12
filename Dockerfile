FROM eclipse-temurin:11.0.26_4-jdk-focal

WORKDIR /app

# Copia todos los recursos necesarios
COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY etc/logback.xml /app/etc/
COPY modules/core/src/com/company/clinic/app-prod.properties /app/config/

# Crea directorios esenciales
RUN mkdir -p /app/logs /app/temp /app/filestorage

# Variables de entorno CRÍTICAS
ENV CUBA_CONF_DIR=/app/config
ENV JAVA_OPTS="-Dserver.port=8080 \
              -Dcuba.webContextName=app \
              -Dlogging.config=/app/etc/logback.xml \
              -Dlogback.configurationFile=/app/etc/logback.xml \
              -Dspring.config.location=file:/app/config/ \
              -XX:+UseContainerSupport \
              -XX:MaxRAMPercentage=75"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]