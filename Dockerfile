FROM eclipse-temurin:11.0.26_4-jdk-focal

WORKDIR /app

# Copia el JAR y configuración
COPY build/distributions/uberJar/clinic.jar app.jar
COPY modules/core/src/com/company/clinic/app-prod.properties /app/config/

# Variables de entorno esenciales
ENV CUBA_CONF_DIR=/app/config \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# Puerto y usuario no root
EXPOSE 8080
USER 1001

ENTRYPOINT ["java", "-jar", "app.jar"]