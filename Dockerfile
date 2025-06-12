FROM eclipse-temurin:11.0.26_4-jdk-focal

# Instala dependencias necesarias para reportes y fuentes
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    tzdata \
    && rm -rf /var/lib/apt/lists/*

# Configura zona horaria a Europa/Madrid
RUN ln -sf /usr/share/zoneinfo/Europe/Madrid /etc/localtime && \
    echo "Europe/Madrid" > /etc/timezone

WORKDIR /app

# Copia el JAR y configuración
COPY build/distributions/uberJar/app.jar app.jar
COPY modules/core/src/com/company/clinic/app-prod.properties /app/config/

# Variables de entorno
ENV CUBA_CONF_DIR=/app/config
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:+TieredCompilation -XX:TieredStopAtLevel=1"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]