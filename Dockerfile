FROM eclipse-temurin:11.0.26_4-jdk-focal

# Instala dependencias
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

# Crea estructura de directorios
RUN mkdir -p /app/{config,logs,temp,work,storage} \
    && chmod -R 755 /app

WORKDIR /app

# Copia el JAR (asegúrate que el build incluye todas las dependencias)
COPY build/distributions/uberJar/clinic.jar /app/app.jar

# Configuraciones (opcional)
COPY etc/logback.xml /app/config/
COPY modules/core/src/com/company/clinic/app.properties /app/config/

# Asegura permisos
RUN chown -R 1000:1000 /app

USER 1000

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]