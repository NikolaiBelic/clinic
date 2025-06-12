FROM eclipse-temurin:11.0.26_4-jdk-focal

WORKDIR /app

# Copia el JAR y configuración
COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY modules/core/src/com/company/clinic/app-prod.properties /app/config/

# Crea directorios necesarios para Vaadin
RUN mkdir -p /app/VAADIN/widgetsets \
    && mkdir -p /app/storage \
    && mkdir -p /app/temp \
    && mkdir -p /app/logs

# Variables de entorno esenciales
ENV CUBA_CONF_DIR=/app/config
ENV JAVA_OPTS="-Dserver.port=8080 \
              -Dcuba.webContextName=app \
              -Dvaadin.productionMode=true \
              -Dvaadin.resources.cacheTime=0 \
              -XX:+UseContainerSupport \
              -XX:MaxRAMPercentage=75"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]