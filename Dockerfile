FROM eclipse-temurin:11.0.26_4-jdk-focal

# Actualiza e instala dependencias necesarias
RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo UberJar generado
COPY build/distributions/uberJar/clinic.jar /app/app.jar

# Crea directorios necesarios con permisos adecuados
RUN mkdir -p /app/{config,logs,temp,storage} \
    && chown -R 1000:1000 /app \
    && chmod -R 755 /app

# Copia configuraciones necesarias
COPY etc/logback.xml /app/config/
COPY modules/core/src/com/company/clinic/app.properties /app/config/

# Cambia el propietario de los archivos de configuración
RUN chown -R 1000:1000 /app/config

# Cambia al usuario no root
USER 1000

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]