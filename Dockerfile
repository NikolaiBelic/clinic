FROM eclipse-temurin:11-jre  # Usar JRE en lugar de JDK para producción

# Instala dependencias necesarias
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    tzdata \
    && rm -rf /var/lib/apt/lists/*

# Configura zona horaria
RUN ln -sf /usr/share/zoneinfo/Europe/Madrid /etc/localtime && \
    echo "Europe/Madrid" > /etc/timezone

WORKDIR /app

# Copia el JAR y configuración
COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY modules/core/src/com/company/clinic/app-prod.properties /app/config/
COPY modules/core/src/com/company/clinic/persistence.xml /app/config/

# Crea directorios necesarios
RUN mkdir -p /app/logs /app/temp /app/filestorage

# Variables de entorno (actualizadas)
ENV CUBA_CONF_DIR=/app/config
ENV JAVA_OPTS="-Djava.awt.headless=true \
               -Dfile.encoding=UTF-8 \
               -XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75 \
               -Dcuba.webContextName=clinic \
               -Dcuba.connectionUrlList=http://localhost:8080/clinic"

EXPOSE 8080

# Entrypoint mejorado
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]