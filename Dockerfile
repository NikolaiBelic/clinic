FROM eclipse-temurin:11.0.26_4-jdk-focal

RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY build/distributions/uberJar/clinic.jar /app/app.jar

RUN mkdir -p /app/config \
    && mkdir -p /app/logs \
    && mkdir -p /app/temp \
    && mkdir -p /app/filestorage

# Asegúrate que el archivo properties tenga permisos adecuados
COPY modules/core/src/com/company/clinic/app.properties /app/config/
RUN chmod 644 /app/config/app.properties

ENV CUBA_CONF_DIR=/app/config \
    JAVA_OPTS="-Dserver.port=8080 \
    -Dcuba.webContextName=app \
    -Dvaadin.productionMode=true \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]