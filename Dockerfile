FROM eclipse-temurin:11.0.26_4-jdk-focal

# Instala dependencias necesarias
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copia el JAR
COPY build/distributions/uberJar/clinic.jar /app/app.jar

# Crea estructura de directorios
RUN mkdir -p /app/config \
    && mkdir -p /app/VAADIN/widgetsets \
    && mkdir -p /app/logs \
    && mkdir -p /app/temp \
    && mkdir -p /app/filestorage

# Variables de entorno
ENV CUBA_CONF_DIR=/app/config
ENV JAVA_OPTS="-Dserver.port=8080 \
              -Dcuba.webContextName=app \
              -Dvaadin.productionMode=false \
              -Dvaadin.widgetset=com.haulmont.cuba.web.widgets.WidgetSet \
              -XX:+UseContainerSupport \
              -XX:MaxRAMPercentage=75"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]