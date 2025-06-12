FROM eclipse-temurin:11.0.26_4-jdk-focal

# Instala dependencias necesarias
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copia configuración y aplicación
COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY build/resources/main/app.properties /app/config/
COPY build/resources/main/logback.xml /app/config/

# Crea estructura de directorios
RUN mkdir -p /app/config \
    && mkdir -p /app/logs \
    && mkdir -p /app/temp \
    && mkdir -p /app/filestorage

# Variables de entorno para SQL Server
ENV CUBA_CONF_DIR=/app/config \
    JAVA_OPTS="-Dserver.port=8080 \
    -Dcuba.webContextName=app \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -Dcuba.dataSource.jdbcUrl=jdbc:sqlserver://host.docker.internal:1433;databaseName=clinic \
    -Dcuba.dataSource.username=anicolcl \
    -Dcuba.dataSource.password=Elalmendro.33"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s \
    CMD curl -f http://localhost:8080/app/ || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]